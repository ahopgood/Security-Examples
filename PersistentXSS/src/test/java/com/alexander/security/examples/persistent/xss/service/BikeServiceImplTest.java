package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.CommentEntity;
import com.alexander.security.examples.persistent.xss.persistence.repository.BikeDetailsRepository;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
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

    private BikeDetailsRepository detailsRepository = mock(BikeDetailsRepository.class);
    private Mapper<BikeDetails, BikeDetailsEntity> bikeDetailsMapper = new BikeDetailsMapper();

    private BikeService bikeService = new BikeServiceImpl(thumbnailRepository,
            bikeThumbnailMapper,
            detailsRepository,
            bikeDetailsMapper);

    private final String commentId = "c1";
    private final String comment = "I rode this bike until it broke";
    private final String description = "A hard tail bike perfect for trails";
    private final String fullUrl = "large/trekticket20.png";

    @Test
    void testGetBikeDetails_whenIdDoesNotExist_thenReturnNull() {
    }

    @Test
    void testGetBikeDetails_whenNoComments() {
        when(detailsRepository.getBikeDetails("b1")).thenReturn(getBikeDetailsEntity());
        BikeDetails details = bikeService.getBikeDetails("b1");
        assertThat(details.getBikeId()).isEqualTo(bikeId);
        assertThat(details.getBikeDescription()).isEqualTo(description);
        assertThat(details.getFullImageUrl()).isEqualTo(fullUrl);
        assertThat(details.getTitle()).isEqualTo(title);
        assertThat(details.getComments()).isNotEmpty();
        assertThat(details.getComments().get(0).getComment()).isEqualTo(comment);

        verify(detailsRepository, times(1)).getBikeDetails("b1");
    }

    @Test
    void testGetBikeDetails_whenNoComments_thenReturnEmptyList() {
        when(detailsRepository.getBikeDetails("b1")).thenReturn(getBikeDetailsEntityWithNoComments());
        BikeDetails details = bikeService.getBikeDetails("b1");
        assertThat(details.getBikeId()).isEqualTo(bikeId);
        assertThat(details.getBikeDescription()).isEqualTo(description);
        assertThat(details.getFullImageUrl()).isEqualTo(fullUrl);
        assertThat(details.getTitle()).isEqualTo(title);
        assertThat(details.getComments()).isEmpty();

        verify(detailsRepository, times(1)).getBikeDetails("b1");
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
    private final String thumbnailId = "t1";
    private final String url = "small/trekticket20.png";
    private final String title = "Trek Ticket 20";

    private BikeThumbnailEntity getBikeThumbnailEntity() {
        return BikeThumbnailEntity.builder()
                .bikeId(bikeId)
                .thumbnailImageUrl(url)
                .title(title)
                .id(thumbnailId)
                .build();
    }

    private BikeDetailsEntity getBikeDetailsEntity() {
        return BikeDetailsEntity.builder()
                .bikeId(bikeId)
                .title(title)
                .description(description)
                .url(fullUrl)
                .commentEntities(List.of(CommentEntity.builder()
                        .comment(comment)
                        .id(commentId)
                        .build())).build();
    }

    private BikeDetailsEntity getBikeDetailsEntityWithNoComments() {
        return BikeDetailsEntity.builder()
                .bikeId(bikeId)
                .title(title)
                .description(description)
                .url(fullUrl)
                .build();
    }
}