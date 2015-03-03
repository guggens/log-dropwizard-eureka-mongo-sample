package com.guggens.log.eureka.server.server;

import com.codahale.metrics.JmxReporter;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<EurekaServerRootConfiguration> {
   public static void main(String[] args) throws Exception {
      new Main().run(new String[] { "server", System.getProperty("dropwizard.config") });
   }

   @Override
   public void initialize(Bootstrap<EurekaServerRootConfiguration> bootstrap) {
      bootstrap.addBundle(new EurekaServerBundle());
   }

   @Override
   public void run(EurekaServerRootConfiguration configuration, Environment environment) throws ClassNotFoundException {
      JmxReporter.forRegistry(environment.metrics()).build().start();
   }
}

