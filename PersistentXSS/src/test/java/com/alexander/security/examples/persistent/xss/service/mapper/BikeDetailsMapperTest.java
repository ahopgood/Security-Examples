package com.alexander.security.examples.persistent.xss.service.mapper;


import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.CommentEntity;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BikeDetailsMapperTest {

    private final String bikeId = "b1";
    private final String title = "Trek Ticket 20";
    private final String commentId = "c1";
    private final String comment = "I rode this bike until it broke";
    private final String description = "A hard tail bike perfect for trails";
    private final String fullUrl = "large/trekticket20.png";

    private Mapper<Optional<BikeDetails>, Optional<BikeDetailsEntity>> mapper = new BikeDetailsMapper();

    @Test
    void testMap() {
        Optional<BikeDetails> detailsOptional = mapper.map(getBikeDetailsEntity());
        BikeDetails details = detailsOptional.get();
        assertThat(details.getBikeId()).isEqualTo(bikeId);
        assertThat(details.getBikeDescription()).isEqualTo(description);
        assertThat(details.getFullImageUrl()).isEqualTo(fullUrl);
        assertThat(details.getTitle()).isEqualTo(title);
        assertThat(details.getComments()).isNotEmpty();
        assertThat(details.getComments().get(0).getComment()).isEqualTo(comment);
    }

    @Test
    void testMap_givenNoComments() {
        Optional<BikeDetails> detailsOptional = mapper.map(getBikeDetailsEntityWithNoComments());
        BikeDetails details = detailsOptional.get();
        assertThat(details.getBikeId()).isEqualTo(bikeId);
        assertThat(details.getBikeDescription()).isEqualTo(description);
        assertThat(details.getFullImageUrl()).isEqualTo(fullUrl);
        assertThat(details.getTitle()).isEqualTo(title);
        assertThat(details.getComments()).isEmpty();
    }

    private Optional<BikeDetailsEntity> getBikeDetailsEntityWithNoComments() {
        return Optional.of(BikeDetailsEntity.builder()
                .bikeId(bikeId)
                .title(title)
                .description(description)
                .url(fullUrl)
                .build());
    }

    private Optional<BikeDetailsEntity> getBikeDetailsEntity() {
        return Optional.of(BikeDetailsEntity.builder()
                .bikeId(bikeId)
                .title(title)
                .description(description)
                .url(fullUrl)
                .comments(List.of(CommentEntity.builder()
                        .comment(comment)
                        .id(commentId)
                        .build()))
                .build());
    }
}