package com.guggens.log.writer;

import com.guggens.log.writer.client.EurekaClientBundle;
import com.guggens.log.writer.mongo.MongoHealthCheck;
import com.guggens.log.writer.mongo.NoDBNameException;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class LogWriterApp extends Application<LogWriterConfiguration> {


    public static void main(String[] args) throws Exception {
        new LogWriterApp().run(args);
    }

    @Override
    public String getName() {
        return "LogWriter";
    }

    @Override
    public void initialize(Bootstrap<LogWriterConfiguration> bootstrap) {
        bootstrap.addBundle(new EurekaClientBundle());
    }

    @Override
    public void run(LogWriterConfiguration configuration,
                    Environment environment) throws UnknownHostException, NoDBNameException {


        final MongoClient mongoClient = configuration.getMongoFactory().buildClient(environment);
        final DB db = configuration.getMongoFactory().buildDB(environment);

        //Register health checks
        environment.healthChecks().register("mongo",new MongoHealthCheck(mongoClient));

        final LogWriterResource resource = new LogWriterResource(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                db
        );
        environment.jersey().register(resource);

        final LogWriterHealthCheck healthCheck =
                new LogWriterHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("logwriter", healthCheck);
        environment.jersey().register(resource);

    }

}
