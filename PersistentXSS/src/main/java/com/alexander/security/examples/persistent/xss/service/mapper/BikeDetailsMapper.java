package com.alexander.security.examples.persistent.xss.service.mapper;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.service.model.Comment;

import java.util.Optional;
import java.util.stream.Collectors;

public class BikeDetailsMapper implements Mapper<Optional<BikeDetails>, Optional<BikeDetailsEntity>> {

    @Override
    public Optional<BikeDetails> map(Optional<BikeDetailsEntity> bikeDetailsEntityOptional) {
        if (bikeDetailsEntityOptional.isPresent()) {
            BikeDetailsEntity bikeDetailsEntity = bikeDetailsEntityOptional.get();
            return Optional.of(BikeDetails.builder()
                    .bikeId(bikeDetailsEntity.getBikeId())
                    .bikeDescription(bikeDetailsEntity.getDescription())
                    .title(bikeDetailsEntity.getTitle())
                    .fullImageUrl(bikeDetailsEntity.getUrl())
                    .comments(bikeDetailsEntity.getComments().stream()
                            .map(comment ->
                                    Comment.builder().comment(comment.getComment()).build()
                            ).collect(Collectors.toList()))
                    .build());
        } else {
            return Optional.empty();
        }

    }
}
