package com.guggens.log.producer.tenacity;


import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yammer.tenacity.core.TenacityCommand;

public class LogReadCommand extends TenacityCommand<String> {

    Client client;

    String logWriterApplicationName;

    public LogReadCommand(Client client, String logWriterApplicationName) {
        super(LogProducerDependencyKeys.LOG_READ);
        this.client = client;
        this.logWriterApplicationName = logWriterApplicationName;
    }

    @Override
    protected String run() throws Exception {

        IRule rule = new AvailabilityFilteringRule();
        ServerList<DiscoveryEnabledServer> list = new DiscoveryEnabledNIWSServerList(logWriterApplicationName);
        ServerListFilter<DiscoveryEnabledServer> filter = new ZoneAffinityServerListFilter<DiscoveryEnabledServer>();
        ZoneAwareLoadBalancer<DiscoveryEnabledServer> lb = LoadBalancerBuilder.<DiscoveryEnabledServer>newBuilder()
                .withDynamicServerList(list)
                .withRule(rule)
                .withServerListFilter(filter)
                .buildDynamicServerListLoadBalancer();
        Server server = lb.chooseServer();

        String url = "http://" + server.getHostPort() + "/log/latest";
        WebResource webResource = client
                .resource(url);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus() + " url " + url);
        }

        String output = response.getEntity(String.class);

        return "ceceived list of size: " + output;
    }
}
