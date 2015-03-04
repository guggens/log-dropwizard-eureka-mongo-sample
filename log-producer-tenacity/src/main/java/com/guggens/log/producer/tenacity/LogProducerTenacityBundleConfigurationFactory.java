package com.guggens.log.producer.tenacity;

import com.google.common.collect.ImmutableMap;
import com.guggens.log.producer.LogProducerConfiguration;
import com.yammer.tenacity.core.bundle.BaseTenacityBundleConfigurationFactory;
import com.yammer.tenacity.core.config.BreakerboxConfiguration;
import com.yammer.tenacity.core.config.TenacityConfiguration;
import com.yammer.tenacity.core.properties.TenacityPropertyKey;
import com.yammer.tenacity.core.properties.TenacityPropertyKeyFactory;

import java.util.Map;

/**
 * Created by GOEBELS on 04.03.2015.
 */
public class LogProducerTenacityBundleConfigurationFactory extends BaseTenacityBundleConfigurationFactory<LogProducerConfiguration> {

    @Override
    public Map<TenacityPropertyKey, TenacityConfiguration> getTenacityConfigurations(LogProducerConfiguration configuration) {
        final ImmutableMap.Builder<TenacityPropertyKey, TenacityConfiguration> builder = ImmutableMap.builder();
        builder.put(LogProducerDependencyKeys.LOG_WRITER, configuration.getLogWriterTenacityConfiguration());
        return builder.build();
    }

    @Override
    public TenacityPropertyKeyFactory getTenacityPropertyKeyFactory(LogProducerConfiguration logProducerConfiguration) {
        return new LogProducerDependencyKeyFactory();
    }

    @Override
    public BreakerboxConfiguration getBreakerboxConfiguration(LogProducerConfiguration configuration) {
        return configuration.getBreakerboxConfiguration();
    }

}
