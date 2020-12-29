package com.alexander.security.examples.persistent.xss.service.mapper;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;

public class BikeThumbnailMapper implements Mapper<BikeThumbnail, BikeThumbnailEntity> {

    @Override
    public BikeThumbnail map(BikeThumbnailEntity bikeThumbnailEntity) {
        return BikeThumbnail.builder()
                .bikeId(bikeThumbnailEntity.getBikeId())
                .title(bikeThumbnailEntity.getTitle())
                .thumbnailImageUrl(bikeThumbnailEntity.getThumbnailImageUrl())
                .build();
    }
}
