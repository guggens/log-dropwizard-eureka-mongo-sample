package com.guggens.log.eureka.server.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.provider.DiscoveryJerseyProvider;
import com.netflix.eureka.EurekaBootStrap;
import com.netflix.eureka.resources.*;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.configuration.BaseConfiguration;

public class EurekaServerBundle implements ConfiguredBundle<ConfiguresEurekaServer> {
   @Override
   public void initialize(Bootstrap<?> bootstrap) {

   }

   @Override
   public void run(ConfiguresEurekaServer configuration, Environment environment) throws Exception {
      BaseConfiguration baseConfiguration = new BaseConfiguration();
      baseConfiguration.setProperty("eureka.waitTimeInMsWhenSyncEmpty", configuration.getEurekaServer().getWaitTimeInMsWhenSyncEmpty());
      baseConfiguration.setProperty("eureka.numberRegistrySyncRetries", configuration.getEurekaServer().getNumberRegistrySyncRetries());
      baseConfiguration.setProperty("eureka.serviceUrl.default", configuration.getEurekaServer().getDefaultServiceUrl());
      baseConfiguration.setProperty("eureka.enableSelfPreservation", configuration.getEurekaServer().getEnableSelfPreservation());
      baseConfiguration.setProperty("eureka.shouldFetchRegistry", false);
      ConfigurationManager.loadPropertiesFromConfiguration(baseConfiguration);

      environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      environment.getObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

      environment.servlets().addServletListeners(new EurekaBootStrap());

      environment.healthChecks().register("eureka", new EurekaServerHealthCheck());

      environment.jersey().register(ApplicationsResource.class);
      environment.jersey().register(InstancesResource.class);
      environment.jersey().register(PeerReplicationResource.class);
      environment.jersey().register(SecureVIPResource.class);
      environment.jersey().register(StatusResource.class);
      environment.jersey().register(VIPResource.class);
      environment.jersey().register(ASGResource.class);
      environment.jersey().register(DiscoveryJerseyProvider.class);

      environment.jersey().getResourceConfig().getSingletons().clear();
      environment.jersey().getResourceConfig().getSingletons().add(new DiscoveryJerseyProvider());
   }
}
