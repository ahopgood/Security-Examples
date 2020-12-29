package com.alexander.security.examples.persistent.xss.service.mapper;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.service.model.Comment;

import java.util.stream.Collectors;

public class BikeDetailsMapper implements Mapper<BikeDetails, BikeDetailsEntity> {

    @Override
    public BikeDetails map(BikeDetailsEntity bikeDetailsEntity) {
        return BikeDetails.builder()
                .bikeId(bikeDetailsEntity.getBikeId())
                .bikeDescription(bikeDetailsEntity.getDescription())
                .title(bikeDetailsEntity.getTitle())
                .fullImageUrl(bikeDetailsEntity.getUrl())
                .comments(bikeDetailsEntity.getComments().stream()
                    .map(comment ->
                        Comment.builder().comment(comment.getComment()).build()
                    ).collect(Collectors.toList()))
                .build();
    }
}
