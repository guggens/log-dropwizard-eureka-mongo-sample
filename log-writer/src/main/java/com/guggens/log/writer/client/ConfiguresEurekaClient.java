package com.guggens.log.writer.client;

import io.dropwizard.server.ServerFactory;

public interface ConfiguresEurekaClient {
   ServerFactory getServerFactory();

   EurekaClientConfiguration getEureka();
}
