package com.alexander.security.examples.persistent.xss.rest.mapper;

import com.alexander.security.examples.persistent.xss.rest.model.BikeDetailsResponse;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.service.model.Comment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class BikeDetailsMapperTest {

    private final String description = "A hard tail bike perfect for trails";
    private final String title = "Trek Ticket 20";
    private final String bikeId = "12";
    private final String fullImageUrl = "large/trekticket20.png";
    private final String comment1 = "I rode this bike until it broke";
    private final String comment2 = "Bought this in the end of season sale, bargain!";

    private final BikeDetailsMapper mapper = new BikeDetailsMapper();

    @Test
    void testMap_givenNoComments_thenEmptyArray() {
        BikeDetailsResponse restBikeDetailsResponse = mapper.map(getNoCommentBikeDetails());
        assertThat(restBikeDetailsResponse.getTitle()).isEqualTo(title);
        assertThat(restBikeDetailsResponse.getBikeDescription()).isEqualTo(description);
        assertThat(restBikeDetailsResponse.getBikeId()).isEqualTo(bikeId);
        assertThat(restBikeDetailsResponse.getFullImageUrl()).isEqualTo(fullImageUrl);

        assertThat(restBikeDetailsResponse.getComments()).isNotNull();
        assertThat(restBikeDetailsResponse.getComments()).isEmpty();
    }

    @Test
    void testMap_givenComments_thenMapIntoArray() {
        BikeDetailsResponse restBikeDetailsResponse = mapper.map(getBikeDetails());
        assertThat(restBikeDetailsResponse.getTitle()).isEqualTo(title);
        assertThat(restBikeDetailsResponse.getBikeDescription()).isEqualTo(description);
        assertThat(restBikeDetailsResponse.getBikeId()).isEqualTo(bikeId);
        assertThat(restBikeDetailsResponse.getFullImageUrl()).isEqualTo(fullImageUrl);

        assertThat(restBikeDetailsResponse.getComments()).isNotNull();
        assertThat(restBikeDetailsResponse.getComments()).isNotEmpty();
        assertThat(restBikeDetailsResponse.getComments()).hasSize(2);
        assertThat(restBikeDetailsResponse.getComments().get(0).getComment()).isEqualTo(comment1);
        assertThat(restBikeDetailsResponse.getComments().get(1).getComment()).isEqualTo(comment2);
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