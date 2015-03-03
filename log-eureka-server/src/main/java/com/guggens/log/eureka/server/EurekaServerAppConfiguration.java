package com.guggens.log.eureka.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guggens.log.eureka.server.server.ConfiguresEurekaServer;
import com.guggens.log.eureka.server.server.EurekaServerConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by GOEBELS on 03.03.2015.
 */
public class EurekaServerAppConfiguration extends Configuration implements ConfiguresEurekaServer {

    @Valid
    @NotNull
    @JsonProperty
    String defaultServiceUrl = "";

    @Override
    public EurekaServerConfiguration getEurekaServer() {
        EurekaServerConfiguration configuration = new EurekaServerConfiguration();
        configuration.setDefaultServiceUrl(defaultServiceUrl);
        return configuration;
    }
}
