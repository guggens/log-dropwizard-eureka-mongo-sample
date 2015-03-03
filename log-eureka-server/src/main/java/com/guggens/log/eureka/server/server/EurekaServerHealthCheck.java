package com.guggens.log.eureka.server.server;

import com.codahale.metrics.health.HealthCheck;

public class EurekaServerHealthCheck extends HealthCheck {
   @Override
   protected Result check() throws Exception {
      return Result.healthy();
   }
}
