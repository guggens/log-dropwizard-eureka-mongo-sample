package com.guggens.log.writer.client;

import java.util.Map;

public interface DiscoveryMetadataProvider {
   Map<String, String> getMetadata();
}
