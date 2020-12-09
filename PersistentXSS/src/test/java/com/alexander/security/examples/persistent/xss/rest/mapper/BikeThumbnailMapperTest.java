package com.alexander.security.examples.persistent.xss.rest.mapper;

import com.alexander.security.examples.persistent.xss.rest.model.BikeThumbnailResponse;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BikeThumbnailMapperTest {

    private final BikeThumbnailMapper mapper = new BikeThumbnailMapper();

    private final String title = "title";
    private final String thumbnailUrl = "https://dirtybikes.com/images/small/trekticket20.png";
    private final String bikeId = "1";

    @Test
    void testMap() {
        BikeThumbnailResponse restBikeThumbnailResponse = mapper.map(getThumbnail());
        assertThat(restBikeThumbnailResponse.getBikeId()).isEqualTo(bikeId);
        assertThat(restBikeThumbnailResponse.getThumbnailImageUrl()).isEqualTo(thumbnailUrl);
        assertThat(restBikeThumbnailResponse.getTitle()).isEqualTo(title);

        assertThat(restBikeThumbnailResponse).hasNoNullFieldsOrProperties();
    }

    private BikeThumbnail getThumbnail() {
        return BikeThumbnail.builder()
                .bikeId(bikeId)
                .thumbnailImageUrl(thumbnailUrl)
                .title(title)
                .build();
    }
}