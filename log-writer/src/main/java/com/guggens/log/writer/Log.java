package com.guggens.log.writer;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;

public class Log {

    @Id
    @JsonProperty
    private Long id;

    @Length(max = 30)
    @JsonProperty
    private String content;


    public Log() {
        // Jackson deserialization
    }

    public Log(Long id, String content) {
        this.id = id;
        this.content = content;
    }

}