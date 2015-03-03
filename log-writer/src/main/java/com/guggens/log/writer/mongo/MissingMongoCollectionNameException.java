package com.guggens.log.writer.mongo;

/**
 * Exception thrown if the {@link MongoFactory} attempts to build
 * a DBCollection object and the configured collection name is null.
 */
public class MissingMongoCollectionNameException extends Exception {
    private static final String message = "Attempt made to create a DBCollection object when t" +
            "he configured collection name was null or invalid";

    public MissingMongoCollectionNameException() {
        super(message);
    }
}