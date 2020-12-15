package com.alexander.security.examples.persistent.xss.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
//@AllArgsConstructor
public class CommentRequest {

    @JsonProperty
    private String comment;

    @JsonCreator
    public CommentRequest(@JsonProperty String comment) {
        this.comment = comment;
    }
}
