package com.guggens.log.writer;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mongodb.DB;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/log")
@Produces(MediaType.APPLICATION_JSON)
public class LogWriterResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    private static final String LOG_COLLECTION = "LOG";

    private DB mongoDB;
    JacksonDBCollection<Log, String> entries;

    public LogWriterResource(String template, String defaultName, DB db) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.mongoDB = db;
        entries = JacksonDBCollection.wrap(mongoDB.getCollection(LOG_COLLECTION), Log.class,
                String.class);
    }

    @GET
    @Timed
    public Log writeLog(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        Log log = new Log(counter.incrementAndGet(), value);
        entries.insert(Collections.singletonList(log));
        return log;
    }

    @GET
    @Path("/list")
    public List<Log> fetch(@PathParam("collection") String collection) {

        final DBCursor<Log> cursor = entries.find();
        final List<Log> l = new ArrayList<Log>();

        try {
            while (cursor.hasNext()) {
                l.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return l;
    }

}