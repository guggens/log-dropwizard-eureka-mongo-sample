package com.guggens.log.writer;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;

public class Log {

    @Id
    @JsonProperty
    private String id;

    @Length(max = 30)
    @JsonProperty
    private String content;

    @JsonProperty
    private Long timestamp;

    public Log() {
        // Jackson deserialization
    }

    public Log(String id, String content) {
        this.id = id;
        this.content = content;
        timestamp = System.currentTimeMillis();
    }



}