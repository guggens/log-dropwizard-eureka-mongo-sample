package com.guggens.log.eureka.server;

import com.guggens.log.eureka.server.server.EurekaServerBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class EurekaServerApp extends Application<EurekaServerAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new EurekaServerApp().run(args);
    }

    @Override
    public String getName() {
        return "LogEurekaServer";
    }

    @Override
    public void initialize(Bootstrap<EurekaServerAppConfiguration> bootstrap) {
        bootstrap.addBundle(new EurekaServerBundle());
    }

    @Override
    public void run(EurekaServerAppConfiguration configuration,
                    Environment environment) throws UnknownHostException {

    }
}

