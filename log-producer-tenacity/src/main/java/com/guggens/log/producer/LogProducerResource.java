package com.guggens.log.producer;

import com.codahale.metrics.annotation.Timed;
import com.guggens.log.producer.tenacity.LogWriteCommand;
import com.sun.jersey.api.client.Client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/produce")
@Produces(MediaType.APPLICATION_JSON)
public class LogProducerResource {

    private Client client;

    private String logWriterApplicationName;

    public LogProducerResource(Client client, String logWriterApplicationName) {
        this.client = client;
        this.logWriterApplicationName = logWriterApplicationName;
    }

    @GET
    @Timed
    public String produce() {
        return new LogWriteCommand(client, logWriterApplicationName).execute();
    }


}