package com.alexander.security.examples.persistent.xss.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private String comment;

    @JsonCreator
    public CommentRequest(@JsonProperty("comment") String comment) {
        this.comment = comment;
    }
}
