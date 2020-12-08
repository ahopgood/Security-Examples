package com.alexander.security.examples.persistent.xss.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BikeThumbnail {
    
    private final String bikeId;
    private final String thumbnailImageUrl;
    private final String title;
}
