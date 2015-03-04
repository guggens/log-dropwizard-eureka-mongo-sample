package com.guggens.log.producer.tenacity;


import com.yammer.tenacity.core.properties.TenacityPropertyKey;
import com.yammer.tenacity.core.properties.TenacityPropertyKeyFactory;

public class LogProducerDependencyKeyFactory implements TenacityPropertyKeyFactory {
    @Override
    public TenacityPropertyKey from(String value) {
        return LogProducerDependencyKeys.valueOf(value.toUpperCase());
    }
}
