package com.alexander.security.examples.persistent.xss.persistence.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentEntity {

    //Table column names
    public static final String ID = "id";
    public static final String COMMENT = "comment";

    private final String id;
    private final String comment;

}
