package com.guggens.log.producer.client;


import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.Collection;

public class EurekaClientBundle implements ConfiguredBundle<ConfiguresEurekaClient> {
   private final Collection<DiscoveryMetadataProvider> discoveryMetadataProviders;

   public EurekaClientBundle() {
      this(new ArrayList<DiscoveryMetadataProvider>());
   }

   public EurekaClientBundle(Collection<DiscoveryMetadataProvider> discoveryMetadataProviders) {
      this.discoveryMetadataProviders = discoveryMetadataProviders;
   }

   @Override
   public void run(ConfiguresEurekaClient configuration, Environment environment) throws Exception {
      EurekaClientConfiguration eurekaConfiguration = configuration.getEureka();
      if(!eurekaConfiguration.getEnabled()) {
         return;
      }

      EurekaClientHealthCheck healthCheck = new EurekaClientHealthCheck();
      EurekaInstance eurekaInstance = new EurekaInstance(configuration, healthCheck, discoveryMetadataProviders);
      environment.lifecycle().manage(eurekaInstance);
      environment.healthChecks().register("eureka", healthCheck);

      environment.admin().addTask(new UpTask(eurekaInstance));
      environment.admin().addTask(new DownTask(eurekaInstance));
   }

   @Override
   public void initialize(Bootstrap<?> bootstrap) {
   }

}
