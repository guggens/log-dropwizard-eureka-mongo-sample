package com.guggens.log.producer;

import com.guggens.log.producer.client.EurekaClientBundle;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class LogProducerApp extends Application<LogProducerConfiguration> {


    public static void main(String[] args) throws Exception {
        new LogProducerApp().run(args);
    }

    @Override
    public String getName() {
        return "LogProducer";
    }

    @Override
    public void initialize(Bootstrap<LogProducerConfiguration> bootstrap) {
        bootstrap.addBundle(new EurekaClientBundle());
    }

    @Override
    public void run(LogProducerConfiguration configuration,
                    Environment environment) throws UnknownHostException {

        final LogProducerHealthCheck healthCheck =
                new LogProducerHealthCheck();
        environment.healthChecks().register("logproducer", healthCheck);


        Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        environment.jersey().register(new LogProducerResource(client, configuration.getLogWriterApplicationName()));
    }

}
