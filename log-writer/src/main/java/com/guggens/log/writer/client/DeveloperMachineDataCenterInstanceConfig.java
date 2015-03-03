package com.guggens.log.writer.client;


import com.netflix.appinfo.PropertiesInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.sun.jersey.spi.resource.Singleton;

import java.util.Collection;
import java.util.Map;

/**
 * An InstanceConfig that makes running multiple services on a single machine easier. Easy machine is
 * given a unique publicly available hostname via the xip.io service from 37signals by combining the vipAddress
 * and the ipAddress.
 */
@Singleton
public class DeveloperMachineDataCenterInstanceConfig extends PropertiesInstanceConfig {
   private static final DynamicPropertyFactory INSTANCE = com.netflix.config.DynamicPropertyFactory.getInstance();
   private final Collection<DiscoveryMetadataProvider> discoveryMetadataProviders;

   public DeveloperMachineDataCenterInstanceConfig(String namespace, Collection<DiscoveryMetadataProvider> discoveryMetadataProviders) {
      super(namespace);
      this.discoveryMetadataProviders = discoveryMetadataProviders;
   }

   @Override
   public String getVirtualHostName() {
      return INSTANCE.getStringProperty(namespace + "vipAddress", null).get();
   }

   @Override
   public String getHostName(boolean refresh) {
      return getVirtualHostName() + "." + super.getIpAddress() + ".xip.io";
   }

   @Override
   public Map<String, String> getMetadataMap() {
      Map<String, String> map = super.getMetadataMap();
      for(DiscoveryMetadataProvider discoveryMetadataProvider : discoveryMetadataProviders) {
         map.putAll(discoveryMetadataProvider.getMetadata());
      }
      return map;
   }
}
