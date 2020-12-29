package com.alexander.security.examples.persistent.xss.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BikeThumbnailResponse {

    private final String bikeId;
    private final String thumbnailImageUrl;
    private final String title;

}
