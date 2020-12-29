package com.alexander.security.examples.persistent.xss.persistence.model;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class BikeDetailsEntity {

    //Table Column names
    public static final String ID = "id";
    public static final String BIKE_ID = "bike_id";
    public static final String URL = "url";
    public static final String DESCRIPTION = "description";
    public static final String COMMENT_ID = "comment_id";

    private final String bikeId;
    private final String title;
    private final String description;
    private final String url;
    @Builder.Default
    private final List<CommentEntity> comments = new LinkedList<>();
}
