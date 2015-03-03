package com.guggens.log.producer;

import com.codahale.metrics.annotation.Timed;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.shared.Application;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/produce")
@Produces(MediaType.APPLICATION_JSON)
public class LogProducerResource {

    private Client client;

    private String logWriterApplicationName;

    public LogProducerResource(Client client, String logWriterApplicationName) {
        this.client = client;
        this.logWriterApplicationName = logWriterApplicationName;
    }

    @GET
    @Timed
    public String produce() {

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