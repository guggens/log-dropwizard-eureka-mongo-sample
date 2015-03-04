package com.guggens.log.producer.client;

import java.util.Map;

public interface DiscoveryMetadataProvider {
   Map<String, String> getMetadata();
}
