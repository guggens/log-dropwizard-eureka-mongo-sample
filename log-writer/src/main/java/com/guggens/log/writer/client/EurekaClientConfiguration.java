package com.guggens.log.writer.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EurekaClientConfiguration {
   @Valid
   @NotNull
   @JsonProperty
   private String name;

   @JsonProperty
   private String groupName;

   @Valid
   @NotNull
   @JsonProperty
   private Integer port;

   @JsonProperty
   private Integer securePort;

   @Valid
   @NotNull
   @JsonProperty
   private String vipAddress;

   @Valid
   @NotNull
   @JsonProperty
   private String defaultServiceUrl;

   @Valid
   @JsonProperty
   private boolean enabled = true;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGroupName() {
      return groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public Integer getPort() {
      return port;
   }

   public void setPort(Integer port) {
      this.port = port;
   }

   public Integer getSecurePort() {
      return securePort;
   }

   public void setSecurePort(Integer securePort) {
      this.securePort = securePort;
   }

   public String getVipAddress() {
      return vipAddress;
   }

   public void setVipAddress(String vipAddress) {
      this.vipAddress = vipAddress;
   }

   public String getDefaultServiceUrl() {
      return defaultServiceUrl;
   }

   public void setDefaultServiceUrl(String defaultServiceUrl) {
      this.defaultServiceUrl = defaultServiceUrl;
   }

   public boolean getEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }
}


