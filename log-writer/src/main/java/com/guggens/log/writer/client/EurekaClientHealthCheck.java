package com.guggens.log.writer.client;

import com.codahale.metrics.health.HealthCheck;
import com.netflix.appinfo.HealthCheckCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EurekaClientHealthCheck extends HealthCheck implements HealthCheckCallback {
   private static final Logger logger = LoggerFactory.getLogger(EurekaClientHealthCheck.class);

   @Override
   protected Result check() throws Exception {
      return Result.healthy();
   }

   @Override
   public boolean isHealthy() {
      try {
         return check().isHealthy();
      }
      catch(Exception e) {
         throw new RuntimeException("Health Check Failed", e);
      }
   }

   //TODO: use non-deprecated class HealthCheckHandler


}
