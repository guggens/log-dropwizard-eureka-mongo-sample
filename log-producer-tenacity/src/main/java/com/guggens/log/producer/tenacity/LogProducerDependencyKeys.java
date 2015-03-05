package com.guggens.log.producer.tenacity;

import com.yammer.tenacity.core.properties.TenacityPropertyKey;

public enum LogProducerDependencyKeys implements TenacityPropertyKey {
    LOG_WRITER,
    LOG_READ
}
