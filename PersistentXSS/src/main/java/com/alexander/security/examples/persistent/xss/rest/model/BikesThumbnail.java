package com.alexander.security.examples.persistent.xss.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BikesThumbnail {


    private final String bikeId;
    private final String imageUrl;
    private final String title;

}
