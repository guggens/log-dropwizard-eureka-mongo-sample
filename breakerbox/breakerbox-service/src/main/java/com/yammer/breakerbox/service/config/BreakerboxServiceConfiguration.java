package com.yammer.breakerbox.service.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import com.yammer.breakerbox.azure.AzureTableConfiguration;
import com.yammer.breakerbox.jdbi.JdbiConfiguration;
import com.yammer.dropwizard.authenticator.LdapConfiguration;
import com.yammer.tenacity.core.config.BreakerboxConfiguration;
import com.yammer.tenacity.core.config.TenacityConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class BreakerboxServiceConfiguration extends Configuration {
    @NotNull @Valid
    private final Optional<AzureTableConfiguration> azure;

    @NotNull @Valid
    private final JerseyClientConfiguration tenacityClient;

    @NotNull @Valid
    private final TenacityConfiguration breakerboxServicesPropertyKeys;

    @NotNull @Valid
    private final TenacityConfiguration breakerboxServicesConfiguration;

    @NotNull @Valid
    private final BreakerboxConfiguration breakerboxConfiguration;


    @NotNull @Valid @JsonProperty("ldap")
    private Optional<LdapConfiguration> ldapConfiguration = Optional.absent();

    @NotNull @Valid
    private ArchaiusOverrideConfiguration archaiusOverride;

    @NotNull @Valid @JsonProperty("database")
    private Optional<JdbiConfiguration> jdbiConfiguration = Optional.absent();

    /* Useful if you are Breakerbox is behind a proxy and not at localhost:8080 */
    @NotNull @Valid
    private HostAndPort breakerboxHostAndPort;

    @NotNull
    private String defaultDashboard;

    @JsonCreator
    public BreakerboxServiceConfiguration(@JsonProperty("azure") AzureTableConfiguration azure,
                                          @JsonProperty("tenacityClient") JerseyClientConfiguration tenacityClientConfiguration,
                                          @JsonProperty("breakerboxServicesPropertyKeys") TenacityConfiguration breakerboxServicesPropertyKeys,
                                          @JsonProperty("breakerboxServicesConfiguration") TenacityConfiguration breakerboxServicesConfiguration,
                                          @JsonProperty("breakerbox") BreakerboxConfiguration breakerboxConfiguration,
                                          @JsonProperty("ldap") LdapConfiguration ldapConfiguration,
                                          @JsonProperty("archaiusOverride") ArchaiusOverrideConfiguration archaiusOverride,
                                          @JsonProperty("database") JdbiConfiguration jdbiConfiguration,
                                          @JsonProperty("breakerboxHostAndPort") HostAndPort breakerboxHostAndPort,
                                          @JsonProperty("defaultDashboard") String defaultDashboard) {
        this.azure = Optional.fromNullable(azure);
        this.tenacityClient = tenacityClientConfiguration;
        this.breakerboxServicesPropertyKeys = Optional.fromNullable(breakerboxServicesPropertyKeys).or(new TenacityConfiguration());
        this.breakerboxServicesConfiguration = Optional.fromNullable(breakerboxServicesConfiguration).or(new TenacityConfiguration());
        this.breakerboxConfiguration = breakerboxConfiguration;
        this.ldapConfiguration = Optional.fromNullable(ldapConfiguration);
        this.archaiusOverride = Optional.fromNullable(archaiusOverride).or(new ArchaiusOverrideConfiguration());
        this.jdbiConfiguration = Optional.fromNullable(jdbiConfiguration);
        this.breakerboxHostAndPort = Optional.fromNullable(breakerboxHostAndPort).or(HostAndPort.fromParts("localhost", 20040));
        this.defaultDashboard = Optional.fromNullable(defaultDashboard).or("production");
    }

    public Optional<AzureTableConfiguration> getAzure() {
        return azure;
    }

    public JerseyClientConfiguration getTenacityClient() {
        return tenacityClient;
    }

    public TenacityConfiguration getBreakerboxServicesPropertyKeys() {
        return breakerboxServicesPropertyKeys;
    }

    public TenacityConfiguration getBreakerboxServicesConfiguration() {
        return breakerboxServicesConfiguration;
    }

    public BreakerboxConfiguration getBreakerboxConfiguration() {
        return breakerboxConfiguration;
    }

    @JsonProperty("ldap")
    public Optional<LdapConfiguration> getLdapConfiguration() {
        return ldapConfiguration;
    }

    public ArchaiusOverrideConfiguration getArchaiusOverride() {
        return archaiusOverride;
    }

    public void setArchaiusOverride(ArchaiusOverrideConfiguration archaiusOverride) {
        this.archaiusOverride = archaiusOverride;
    }

    public void setLdapConfiguration(Optional<LdapConfiguration> ldapConfiguration) {
        this.ldapConfiguration = ldapConfiguration;
    }

    @JsonProperty("database")
    public Optional<JdbiConfiguration> getJdbiConfiguration() {
        return jdbiConfiguration;
    }

    public void setJdbiConfiguration(Optional<JdbiConfiguration> jdbiConfiguration) {
        this.jdbiConfiguration = jdbiConfiguration;
    }

    public HostAndPort getBreakerboxHostAndPort() {
        return breakerboxHostAndPort;
    }

    public String getDefaultDashboard() {
        return defaultDashboard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(azure, tenacityClient, breakerboxServicesPropertyKeys, breakerboxServicesConfiguration, breakerboxConfiguration, ldapConfiguration, archaiusOverride, jdbiConfiguration, breakerboxHostAndPort, defaultDashboard);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BreakerboxServiceConfiguration other = (BreakerboxServiceConfiguration) obj;
        return Objects.equals(this.azure, other.azure) && Objects.equals(this.tenacityClient, other.tenacityClient) && Objects.equals(this.breakerboxServicesPropertyKeys, other.breakerboxServicesPropertyKeys) && Objects.equals(this.breakerboxServicesConfiguration, other.breakerboxServicesConfiguration) && Objects.equals(this.breakerboxConfiguration, other.breakerboxConfiguration) && Objects.equals(this.ldapConfiguration, other.ldapConfiguration) && Objects.equals(this.archaiusOverride, other.archaiusOverride) && Objects.equals(this.jdbiConfiguration, other.jdbiConfiguration) && Objects.equals(this.breakerboxHostAndPort, other.breakerboxHostAndPort) && Objects.equals(this.defaultDashboard, other.defaultDashboard);
    }
}