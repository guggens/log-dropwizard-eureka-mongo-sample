package com.yammer.breakerbox.jdbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.yammer.breakerbox.store.DependencyId;
import com.yammer.breakerbox.store.ServiceId;
import com.yammer.breakerbox.store.model.DependencyModel;
import com.yammer.breakerbox.store.model.ServiceModel;
import com.yammer.tenacity.core.config.TenacityConfiguration;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class Mappers {
    public static class ServiceModelMapper implements ResultSetMapper<ServiceModel> {
        @Override
        public ServiceModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new ServiceModel(ServiceId.from(r.getString("name")), DependencyId.from(r.getString("dependency")));
        }
    }

    public static class DependencyModelMapper implements ResultSetMapper<DependencyModel> {
        private static final Logger LOGGER = LoggerFactory.getLogger(DependencyModelMapper.class);
        private static final ObjectMapper OBJECT_MAPPER = Jackson.newObjectMapper();
        private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

        @Override
        public DependencyModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new DependencyModel(
                    DependencyId.from(r.getString("name")),
                    new DateTime(r.getLong("timestamp")),
                    parseTenacityConfiguration(r.getString("tenacity_configuration")).get(),
                    r.getString("username"),
                    ServiceId.from(r.getString("service")));
        }

        private static Optional<TenacityConfiguration> parseTenacityConfiguration(String tenacityString) {
            try {
                final TenacityConfiguration dependencyConfiguration = OBJECT_MAPPER.readValue(tenacityString, TenacityConfiguration.class);
                final Set<?> validationErrors = VALIDATOR.validate(dependencyConfiguration);
                if (!validationErrors.isEmpty()) {
                    LOGGER.warn("Failed to validate TenacityConfiguration", validationErrors.toString());
                }
                return Optional.of(dependencyConfiguration);
            } catch (Exception err) {
                LOGGER.warn("Failed to parse TenacityConfiguration", err);
            }
            return Optional.absent();
        }
    }
}