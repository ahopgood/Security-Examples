package com.alexander.security.examples.persistent.xss.rest.mapper;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.rest.model.BikeDetailsResponse;
import com.alexander.security.examples.persistent.xss.rest.model.Comment;

import java.util.stream.Collectors;

public class BikeDetailsMapper implements Mapper<BikeDetailsResponse, com.alexander.security.examples.persistent.xss.service.model.BikeDetails> {

    @Override
    public BikeDetailsResponse map(com.alexander.security.examples.persistent.xss.service.model.BikeDetails bikeDetails) {
        return BikeDetailsResponse.builder()
                .bikeDescription(bikeDetails.getBikeDescription())
                .bikeId(bikeDetails.getBikeId())
                .fullImageUrl(bikeDetails.getFullImageUrl())
                .title(bikeDetails.getTitle())
                .comments(bikeDetails.getComments().stream()
                        .map( comment -> Comment.builder()
                                .comment(comment.getComment())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
