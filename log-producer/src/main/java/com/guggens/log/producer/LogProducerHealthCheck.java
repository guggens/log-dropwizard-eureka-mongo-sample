package com.guggens.log.producer;

import com.codahale.metrics.health.HealthCheck;

public class LogProducerHealthCheck extends HealthCheck {


    public LogProducerHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
