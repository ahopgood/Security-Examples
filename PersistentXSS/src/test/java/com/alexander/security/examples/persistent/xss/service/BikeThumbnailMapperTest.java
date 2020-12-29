package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BikeThumbnailMapperTest {

    private final String bikeId = "b1";
    private final String id = "t1";
    private final String url = "small/trekticket20.png";
    private final String title = "Trek Ticket 20";


    private Mapper<BikeThumbnail, BikeThumbnailEntity> mapper = new BikeThumbnailMapper();

    @Test
    void testMap() {
        BikeThumbnail thumbnail = mapper.map(BikeThumbnailEntity.builder()
                .bikeId(bikeId)
                .id(id)
                .thumbnailImageUrl(url)
                .title(title)
                .build());

        assertThat(thumbnail.getBikeId()).isEqualTo(bikeId);
        assertThat(thumbnail.getThumbnailImageUrl()).isEqualTo(url);
        assertThat(thumbnail.getTitle()).isEqualTo(title);
        assertThat(thumbnail).hasNoNullFieldsOrProperties();
    }
}