package com.guggens.log.writer.mongo;

/**
 * Exception thrown if the {@link com.eeb.dropwizardmongo.factory.MongoFactory} attempts to build
 * a DB object and the configured database name is null.
 */
public class NoDBNameException extends Exception {

        private static final String message = "Attempt made to create a DB object when the configured database name was null or invalid";

        public NoDBNameException() {
            super(message);
        }

    }
