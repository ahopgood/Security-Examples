package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import com.alexander.security.examples.persistent.xss.persistence.repository.BikeThumbnailRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BikeServiceImplTest {

    private BikeThumbnailRepository thumbnailRepository = mock(BikeThumbnailRepository.class);
    private Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper = new BikeThumbnailMapper();

    private BikeService bikeService = new BikeServiceImpl(thumbnailRepository, bikeThumbnailMapper);

    @Test
    void testGetBikeDetails_givenNoId() {
    }

    @Test
    void testGetBikeDetails_whenNoComments_thenReturnEmptyList() {

    }

    @Test
    void testGetBikeThumbnails() {
        when(thumbnailRepository.getBikeThumbnails()).thenReturn(List.of(getBikeThumbnailEntity()));
        List<BikeThumbnail> thumbnailList = bikeService.getBikeThumbnails();

        assertThat(thumbnailList).hasSize(1);
        BikeThumbnail thumbnail = thumbnailList.get(0);
        assertThat(thumbnail.getBikeId()).isEqualTo(bikeId);
        assertThat(thumbnail.getThumbnailImageUrl()).isEqualTo(url);
        assertThat(thumbnail.getTitle()).isEqualTo(title);

        verify(thumbnailRepository, times(1)).getBikeThumbnails();
    }

    private final String bikeId = "b1";
    private final String id = "t1";
    private final String url = "small/trekticket20.png";
    private final String title = "Trek Ticket 20";

    private BikeThumbnailEntity getBikeThumbnailEntity() {
        return BikeThumbnailEntity.builder()
                .bikeId(bikeId)
                .thumbnailImageUrl(url)
                .title(title)
                .id(id)
                .build();
    }

}