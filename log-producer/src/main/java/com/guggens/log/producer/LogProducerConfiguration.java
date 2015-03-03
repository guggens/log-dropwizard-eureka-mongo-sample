package com.guggens.log.producer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guggens.log.producer.client.ConfiguresEurekaClient;
import com.guggens.log.producer.client.EurekaClientConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class LogProducerConfiguration extends Configuration implements ConfiguresEurekaClient {


    @Valid
    @NotNull
    @JsonProperty
    private String name;

    @Valid
    @NotNull
    @JsonProperty
    private Integer port;

    @Valid
    @NotNull
    @JsonProperty
    private String vipAddress;

    @Valid
    @NotNull
    @JsonProperty
    private String defaultServiceUrl;

    @Valid
    @NotNull
    @JsonProperty
    private String logWriterApplicationName;

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }

    @Override
    public EurekaClientConfiguration getEureka() {
        EurekaClientConfiguration configuration = new EurekaClientConfiguration();
        configuration.setName(name);
        configuration.setPort(port);
        configuration.setVipAddress(vipAddress);
        configuration.setDefaultServiceUrl(defaultServiceUrl);

        return configuration;
    }

    public String getLogWriterApplicationName() {
        return logWriterApplicationName;
    }
}