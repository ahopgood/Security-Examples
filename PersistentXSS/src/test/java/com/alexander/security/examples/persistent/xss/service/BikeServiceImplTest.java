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
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BikeServiceImplTest {

    private BikeThumbnailRepository thumbnailRepository = mock(BikeThumbnailRepository.class);
    private Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper = new BikeThumbnailMapper();

    private BikeDetailsRepository detailsRepository = mock(BikeDetailsRepository.class);
    private Mapper<Optional<BikeDetails>, Optional<BikeDetailsEntity>> bikeDetailsMapper = new BikeDetailsMapper();

    private BikeService bikeService = new BikeServiceImpl(thumbnailRepository,
            bikeThumbnailMapper,
            detailsRepository,
            bikeDetailsMapper);

    private final String bikeId = "b1";
    private final String unknownId = "x";
    private final String commentId = "c1";
    private final String comment = "I rode this bike until it broke";
    private final String description = "A hard tail bike perfect for trails";
    private final String fullUrl = "large/trekticket20.png";

    private final String thumbnailId = "t1";
    private final String url = "small/trekticket20.png";
    private final String title = "Trek Ticket 20";

    @Test
    void testGetBikeDetails_whenIdDoesNotExist_thenReturnNull() {
    }

    @Test
    void testGetBikeDetails_whenNoComments() {
        when(detailsRepository.getBikeDetails(bikeId)).thenReturn(getBikeDetailsEntity());
        Optional<BikeDetails> detailsOptional = bikeService.getBikeDetails(bikeId);
        BikeDetails details = detailsOptional.get();
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
        when(detailsRepository.getBikeDetails(bikeId)).thenReturn(getBikeDetailsEntityWithNoComments());
        Optional<BikeDetails> bikeDetailsOptional = bikeService.getBikeDetails(bikeId);
        BikeDetails details = bikeDetailsOptional.get();
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

    private BikeThumbnailEntity getBikeThumbnailEntity() {
        return BikeThumbnailEntity.builder()
                .bikeId(bikeId)
                .thumbnailImageUrl(url)
                .title(title)
                .id(thumbnailId)
                .build();
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
                        .build())).build());
    }

    private Optional<BikeDetailsEntity> getBikeDetailsEntityWithNoComments() {
        return Optional.of(BikeDetailsEntity.builder()
                .bikeId(bikeId)
                .title(title)
                .description(description)
                .url(fullUrl)
                .build());
    }


    @Test
    void testGetImage() {
        ClassPathResource resource = bikeService.getImage("images/large/trekticket20.jpg");
        assertThat(resource.exists()).isTrue();
    }

    @Test
    void testGetImage_givenUnknownPath() {
        ClassPathResource resource = bikeService.getImage("images/medium/trekticket20.jpg");
        assertThat(resource.exists()).isFalse();
    }

    @Test
    void testAddComment() {
        when(detailsRepository.addComment(bikeId, comment)).thenReturn(1);
        assertThat(bikeService.addComment(bikeId, comment)).isTrue();
    }

    @Test
    void testAddComment_givenUnknownBike() {
        when(detailsRepository.addComment(unknownId, comment)).thenReturn(0);
        assertThat(bikeService.addComment(unknownId, comment)).isFalse();
    }
}