package com.guggens.log.producer.tenacity;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.shared.Application;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yammer.tenacity.core.TenacityCommand;

import java.util.Random;

public class LogWriteCommand extends TenacityCommand<String> {

    Client client;

    String logWriterApplicationName;

    public LogWriteCommand(Client client, String logWriterApplicationName) {
        super(LogProducerDependencyKeys.LOG_WRITER);
        this.client = client;
        this.logWriterApplicationName = logWriterApplicationName;
    }

    @Override
    protected String run() throws Exception {
        Application telematicLogWriter = DiscoveryManager.getInstance().getDiscoveryClient().getApplication(logWriterApplicationName);

        if (telematicLogWriter == null || telematicLogWriter.getInstances().isEmpty()) {
            return "broken";
        }

        InstanceInfo instanceInfo = telematicLogWriter.getInstances().iterator().next();

        int number = new Random().nextInt();
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/log?name=ProducedLog" + number;
        WebResource webResource = client
                .resource(url);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus() + " url " + url);
        }

        String output = response.getEntity(String.class);

        return "fired " + number + " to url:" + url + " with result " + output;
    }
}
