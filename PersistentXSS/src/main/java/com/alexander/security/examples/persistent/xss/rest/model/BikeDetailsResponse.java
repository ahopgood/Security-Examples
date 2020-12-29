package com.alexander.security.examples.persistent.xss.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class BikeDetailsResponse {
    private final String bikeId;
    private final String fullImageUrl;
    private final String bikeDescription;
    private final String title;
    @Builder.Default
    private final List<Comment> comments = new LinkedList<>();
}
