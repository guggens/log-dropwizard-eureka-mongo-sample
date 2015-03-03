package com.guggens.log.eureka.server.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EurekaServerRootConfiguration extends Configuration implements ConfiguresEurekaServer {
   @Valid
   @NotNull
   @JsonProperty("eurekaServer")
   private final EurekaServerConfiguration eurekaServer = new EurekaServerConfiguration();

   @Override
   public EurekaServerConfiguration getEurekaServer() {
      return eurekaServer;
   }
}
