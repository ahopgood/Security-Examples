package com.alexander.security.examples.persistent.xss.persistence.model;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class BikeDetailsEntity {

    //Table Column names
    private static final String URL = "url";
    private static final String DESCRIPTION = "description";

    private final String bikeId;
    private final String title;
    private final String description;
    private final String url;
    @Builder.Default
    private final List<CommentEntity> commentEntities = new LinkedList<>();
}
