package com.alexander.security.examples.persistent.xss.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BikeThumbnailEntity {

    //Table column names
    public static final String ID = "id";
    public static final String URL = "url";

    private final String id;
    private final String bikeId;
    private final String thumbnailImageUrl;
    private final String title;
}
