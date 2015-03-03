package com.guggens.log.eureka.server.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EurekaServerConfiguration {
   @Valid
   @NotNull
   @JsonProperty
   private Integer waitTimeInMsWhenSyncEmpty = 0;

   @Valid
   @NotNull
   @JsonProperty
   private Integer numberRegistrySyncRetries = 0;

   @Valid
   @JsonProperty
   private Boolean enableSelfPreservation = true;

   @Valid
   @NotNull
   @JsonProperty
   private String defaultServiceUrl;

   public Integer getWaitTimeInMsWhenSyncEmpty() {
      return waitTimeInMsWhenSyncEmpty;
   }

   public void setWaitTimeInMsWhenSyncEmpty(Integer waitTimeInMsWhenSyncEmpty) {
      this.waitTimeInMsWhenSyncEmpty = waitTimeInMsWhenSyncEmpty;
   }

   public Integer getNumberRegistrySyncRetries() {
      return numberRegistrySyncRetries;
   }

   public void setNumberRegistrySyncRetries(Integer numberRegistrySyncRetries) {
      this.numberRegistrySyncRetries = numberRegistrySyncRetries;
   }

   public String getDefaultServiceUrl() {
      return defaultServiceUrl;
   }

   public void setDefaultServiceUrl(String defaultServiceUrl) {
      this.defaultServiceUrl = defaultServiceUrl;
   }

   public Boolean getEnableSelfPreservation() {
      return enableSelfPreservation;
   }

   public void setEnableSelfPreservation(Boolean enableSelfPreservation) {
      this.enableSelfPreservation = enableSelfPreservation;
   }
}

