package com.alexander.security.examples.persistent.xss.rest.model;

import lombok.Builder;

import java.util.List;

@Builder
public class BikeDetails {
    private final String fullImageUrl;
    private final String bikeDescription;
    private final List<Comment> commentList;
}
