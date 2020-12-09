package com.alexander.security.examples.persistent.xss.rest.mapper;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.rest.model.BikeThumbnailResponse;
import org.springframework.stereotype.Component;

@Component
public class BikeThumbnailMapper implements Mapper<BikeThumbnailResponse, com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail> {

    @Override
    public BikeThumbnailResponse map(com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail bikeThumbnail) {
        return BikeThumbnailResponse.builder()
                .bikeId(bikeThumbnail.getBikeId())
                .thumbnailImageUrl(bikeThumbnail.getThumbnailImageUrl())
                .title(bikeThumbnail.getTitle())
                .build();
    }
}
