package com.guggens.log.writer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.guggens.log.writer.client.ConfiguresEurekaClient;
import com.guggens.log.writer.client.EurekaClientConfiguration;
import com.guggens.log.writer.mongo.MongoFactory;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class LogWriterConfiguration extends Configuration implements ConfiguresEurekaClient {


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
    private MongoFactory mongoFactory = new MongoFactory();

    @JsonCreator
    public LogWriterConfiguration(@JsonProperty("mongoDB") MongoFactory mongoFactory) {
        this.mongoFactory = mongoFactory;
    }

    @JsonProperty("mongoDB")
    public MongoFactory getMongoFactory() {
        return this.mongoFactory;
    }

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
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
}