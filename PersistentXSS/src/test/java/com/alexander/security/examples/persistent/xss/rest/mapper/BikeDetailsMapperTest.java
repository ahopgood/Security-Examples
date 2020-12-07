package com.alexander.security.examples.persistent.xss.rest.mapper;

import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.service.model.Comment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class BikeDetailsMapperTest {

    private final String description = "A hard tail bike perfect for trails";
    private final String title = "Trek Ticket 20";
    private final String bikeId = "12";
    private final String fullImageUrl = "https://dirtybikes.com/trekticket20.png";
    private final String comment1 = "I rode this bike until it broke";
    private final String comment2 = "Bought this in the end of season sale, bargain!";

    private final BikeDetailsMapper mapper = new BikeDetailsMapper();

    @Test
    void testMap_givenNoComments_thenEmptyArray() {
        com.alexander.security.examples.persistent.xss.rest.model.BikeDetails restBikeDetails = mapper.map(getNoCommentBikeDetails());
        assertThat(restBikeDetails.getTitle()).isEqualTo(title);
        assertThat(restBikeDetails.getBikeDescription()).isEqualTo(description);
        assertThat(restBikeDetails.getBikeId()).isEqualTo(bikeId);
        assertThat(restBikeDetails.getFullImageUrl()).isEqualTo(fullImageUrl);

        assertThat(restBikeDetails.getComments()).isNotNull();
        assertThat(restBikeDetails.getComments()).isEmpty();
    }

    @Test
    void testMap_givenComments_thenMapIntoArray() {
        com.alexander.security.examples.persistent.xss.rest.model.BikeDetails restBikeDetails = mapper.map(getBikeDetails());
        assertThat(restBikeDetails.getTitle()).isEqualTo(title);
        assertThat(restBikeDetails.getBikeDescription()).isEqualTo(description);
        assertThat(restBikeDetails.getBikeId()).isEqualTo(bikeId);
        assertThat(restBikeDetails.getFullImageUrl()).isEqualTo(fullImageUrl);

        assertThat(restBikeDetails.getComments()).isNotNull();
        assertThat(restBikeDetails.getComments()).isNotEmpty();
        assertThat(restBikeDetails.getComments()).hasSize(2);
        assertThat(restBikeDetails.getComments().get(0).getComment()).isEqualTo(comment1);
        assertThat(restBikeDetails.getComments().get(1).getComment()).isEqualTo(comment2);
    }

    public BikeDetails getBikeDetails() {
        return BikeDetails.builder()
                .bikeDescription(description)
                .fullImageUrl(fullImageUrl)
                .title(title)
                .bikeId(bikeId)
                .comments(
                        Arrays.asList(
                                Comment.builder()
                                        .comment(comment1)
                                        .build(),
                                Comment.builder()
                                        .comment(comment2)
                                        .build())
                )
                .build();
    }

    public BikeDetails getNoCommentBikeDetails() {
        return BikeDetails.builder()
                .bikeDescription(description)
                .fullImageUrl(fullImageUrl)
                .title(title)
                .bikeId(bikeId)
                .build();
    }
}