package com.guggens.log.producer.client;

import io.dropwizard.server.ServerFactory;

public interface ConfiguresEurekaClient {
   ServerFactory getServerFactory();

   EurekaClientConfiguration getEureka();
}
