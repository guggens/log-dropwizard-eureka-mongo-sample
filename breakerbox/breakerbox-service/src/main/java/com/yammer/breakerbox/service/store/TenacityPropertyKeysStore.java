package com.yammer.breakerbox.service.store;

import com.google.common.base.Function;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.yammer.breakerbox.service.tenacity.TenacityPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * TenacityPropertyKeysStore is a component used to track known keys for presentation in the Breakerbox
 * configuration front-end. Adding keys to any externally hosted data structures happens outside the
 * context of this class.
 */
public class TenacityPropertyKeysStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenacityPropertyKeysStore.class);
    private final Cache<URI, ImmutableList<String>> tenacityPropertyKeyCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();
    private final TenacityPoller.Factory tenacityPollerFactory;

    public TenacityPropertyKeysStore(TenacityPoller.Factory tenacityPollerFactory) {
        this.tenacityPollerFactory = tenacityPollerFactory;
    }

    public ImmutableList<String> getTenacityPropertyKeys(final URI uri) {
        try {
            return tenacityPropertyKeyCache.get(uri, new Callable<ImmutableList<String>>() {
                @Override
                public ImmutableList<String> call() throws Exception {
                    return tenacityPollerFactory.create(uri).execute().orNull();
                }
            });
        } catch (CacheLoader.InvalidCacheLoadException err) {
            //null was returned
        } catch (Exception err) {
            LOGGER.warn("Unexpected exception", err);
        }
        return ImmutableList.of();
    }

    public ImmutableSet<String> tenacityPropertyKeysFor(Iterable<URI> uris) {
        return FluentIterable
                .from(uris)
                .transformAndConcat(new Function<URI, ImmutableList<String>>() {
                    @Override
                    public ImmutableList<String> apply(URI input) {
                        return getTenacityPropertyKeys(input);
                    }
                })
                .toSet();
    }
}
