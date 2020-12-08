package com.alexander.security.examples.persistent.xss.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
public class BikeDetails {
    private final String bikeId;
    private final String fullImageUrl;
    private final String bikeDescription;
    private final String title;
    @Builder.Default
    private final List<Comment> comments = new LinkedList<>();
}
