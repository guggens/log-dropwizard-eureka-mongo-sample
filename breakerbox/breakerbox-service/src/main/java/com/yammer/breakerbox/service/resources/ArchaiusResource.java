package com.yammer.breakerbox.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.yammer.breakerbox.service.archaius.ArchaiusFormatBuilder;
import com.yammer.breakerbox.service.config.ArchaiusOverrideConfiguration;
import com.yammer.breakerbox.store.BreakerboxStore;
import com.yammer.breakerbox.store.ServiceId;
import com.yammer.breakerbox.store.model.DependencyModel;
import com.yammer.breakerbox.store.model.ServiceModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/archaius/{service}")
public class ArchaiusResource {
    private final ArchaiusOverrideConfiguration archaiusOverride;
    private final BreakerboxStore breakerboxStore;

    public ArchaiusResource(ArchaiusOverrideConfiguration archaiusOverride,
                            BreakerboxStore breakerboxStore) {
        this.archaiusOverride = archaiusOverride;
        this.breakerboxStore = breakerboxStore;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String getServiceConfigurations(@PathParam("service") String service) {
        final ArchaiusFormatBuilder archaiusBuilder = ArchaiusFormatBuilder.builder();
        final ServiceId serviceId = ServiceId.from(service);
        final Iterable<ServiceModel> propertyKeys = breakerboxStore.listDependenciesFor(serviceId);
        for (ServiceModel propertyKey : propertyKeys) {
            final Optional<DependencyModel> dependencyEntity = breakerboxStore.retrieveLatest(propertyKey.getDependencyId(), serviceId);
            if (dependencyEntity.isPresent()) {
                archaiusBuilder.with(propertyKey.getDependencyId(), dependencyEntity.get().getTenacityConfiguration());
            }
        }
        archaiusBuilder.hystrixMetricsStreamServletMaxConnections(archaiusOverride.getHystrixMetricsStreamServletMaxConnections());
        archaiusBuilder.turbineHostRetryMillis(archaiusOverride.getTurbineHostRetry().toMilliseconds());
        archaiusBuilder.turbineInstanceMonitorLatencyThreshold(archaiusOverride.getTurbineLatencyThreshold().toMilliseconds());
        archaiusBuilder.turbineInstanceMonitorSkipLineDelay(archaiusOverride.getTurbineSkipLineDelay().toMilliseconds());
        return archaiusBuilder.build();
    }
}