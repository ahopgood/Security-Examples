package com.alexander.security.examples.persistent.xss.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {

    private final String comment;

    @JsonCreator
    public Comment(String comment) {
        this.comment = comment;
    }
}
