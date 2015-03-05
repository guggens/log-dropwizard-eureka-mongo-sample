package com.guggens.log.producer;

import com.guggens.log.producer.client.EurekaClientBundle;
import com.guggens.log.producer.tenacity.LogProducerTenacityBundleConfigurationFactory;
import com.guggens.log.producer.tenacity.LogReadCommand;
import com.guggens.log.producer.tenacity.LogWriteCommand;
import com.sun.jersey.api.client.Client;
import com.yammer.tenacity.core.bundle.TenacityBundleBuilder;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import it.sauronsoftware.cron4j.Scheduler;

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
        bootstrap.addBundle(TenacityBundleBuilder
                .<LogProducerConfiguration> newBuilder()
                .configurationFactory(new LogProducerTenacityBundleConfigurationFactory())
                .build());
    }

    @Override
    public void run(final LogProducerConfiguration configuration,
                    Environment environment) throws UnknownHostException {

        final LogProducerHealthCheck healthCheck =
                new LogProducerHealthCheck();
        environment.healthChecks().register("logproducer", healthCheck);


        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        environment.jersey().register(new LogProducerResource(client, configuration.getLogWriterApplicationName()));


        // Creates a Scheduler instance.
        Scheduler s = new Scheduler();
        // Schedule a once-a-minute task.
        s.schedule("* * * * *", new Runnable() {
            public void run() {
                for (int i = 0; i < Integer.parseInt(configuration.getLogsWrittenPerMinute()); i++) {
                    String result = new LogWriteCommand(client, configuration.getLogWriterApplicationName()).execute();
                    try {
                        Thread.sleep(Long.parseLong(configuration.getLogsWrittenWaitBetweenCommandsInMilliseconds()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        s.start();

        s = new Scheduler();
        // Schedule a once-a-minute task.
        s.schedule("* * * * *", new Runnable() {
            public void run() {
                for (int i = 0; i < 60; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new LogReadCommand(client, configuration.getLogWriterApplicationName()).execute();
                }
            }
        });
        // Starts the scheduler.
        s.start();
    }

}
