package com.alexander.security.examples.persistent.xss.rest;

import com.alexander.security.examples.persistent.xss.rest.advice.ExceptionHandler;
import com.alexander.security.examples.persistent.xss.rest.mapper.BikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.rest.mapper.BikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import com.alexander.security.examples.persistent.xss.service.BikeService;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BikesControllerTest {

    private static final String THUMBNAIL_URL = "/bikes/thumbnails";
    private static final String BIKE_ID = "1";
    private static final String DETAIL_URL = "/bikes/detail/" + BIKE_ID;

    private static final String IMAGE_ID = "trekticket20.jpg";

    private static final String IMAGES_PREFIX = "images/large/";
    private static final String IMAGES_URL_PREFIX = "/bikes/" + IMAGES_PREFIX;
    private static final String IMAGES_URL = IMAGES_URL_PREFIX + IMAGE_ID;
    private static final String IMAGE_PATH = IMAGES_PREFIX + IMAGE_ID;

    private static final String THUMBNAIL_IMAGE_PREFIX = "images/small/";
    private static final String THUMBNAIL_IMAGE_URL_PREFIX = "/bikes/" + THUMBNAIL_IMAGE_PREFIX;
    private static final String THUMBNAIL_IMAGE_URL = THUMBNAIL_IMAGE_URL_PREFIX + IMAGE_ID;
    private static final String THUMBNAIL_PATH = THUMBNAIL_IMAGE_PREFIX + IMAGE_ID;

    private static final String COMMENTS_IMAGE_URL = "/bikes/" + BIKE_ID + "/comments/";

    private static final String MADEUP_IMAGE_NAME = "madeup.jpg";

    private static final String COMMENT = "{\"comment\":\"Test Comment\"}";

    private static final HttpMessageConverter MESSAGE_CONVERTER
            = new MappingJackson2HttpMessageConverter(new ObjectMapper());

    private final BikeService bikeService = mock(BikeService.class);
    private final BikeDetailsMapper detailsMapper = new BikeDetailsMapper();
    private final BikeThumbnailMapper thumbnailMapper = new BikeThumbnailMapper();

    private final BikesController controller = new BikesController(bikeService, detailsMapper, thumbnailMapper);

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(MESSAGE_CONVERTER,
                    new ResourceHttpMessageConverter()
            )
            .setControllerAdvice(ExceptionHandler.class)
            .build();

    private final String title = "title";
    private final String thumbnailUrl = "https://dirtybikes.com/images/small/trekticket20.png";
    private final BikeThumbnail thumbnail = BikeThumbnail.builder()
            .bikeId(BIKE_ID)
            .title(title)
            .thumbnailImageUrl(thumbnailUrl)
            .build();

    private final String fullImageUrl = "https://dirtybikes.com/images/large/trekticket20.png";
    private final String description = "What a bouncer";
    private final BikeDetails bikeDetails = BikeDetails.builder()
            .bikeId(BIKE_ID)
            .title(title)
            .fullImageUrl(fullImageUrl)
            .bikeDescription(description)
            .build();

    @BeforeEach
    void setUp() {
        when(bikeService.getBikeThumbnails()).thenReturn(List.of(thumbnail));
        when(bikeService.getBikeDetails(BIKE_ID)).thenReturn(bikeDetails);
        when(bikeService.getImage(IMAGE_PATH))
                .thenReturn(new ClassPathResource(IMAGE_PATH));
        when(bikeService.getImage(THUMBNAIL_PATH))
                .thenReturn(new ClassPathResource(THUMBNAIL_PATH));
        when(bikeService.getImage(IMAGES_PREFIX + MADEUP_IMAGE_NAME))
                .thenReturn(new ClassPathResource(MADEUP_IMAGE_NAME));
        when(bikeService.getImage(THUMBNAIL_IMAGE_PREFIX + MADEUP_IMAGE_NAME))
                .thenReturn(new ClassPathResource(MADEUP_IMAGE_NAME));

    }

    @Test
    void testGetBikeThumbnails_givenWrongVerb() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.head(THUMBNAIL_URL)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.post(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        verify(bikeService, never()).getBikeThumbnails();
    }

    @Test
    void testGetBikeThumbnails() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"bikeId\":\"1\", " +
                                "\"thumbnailImageUrl\":\"https://dirtybikes.com/images/small/trekticket20.png\", " +
                                "\"title\":\"title\"}]"));

        verify(bikeService, times(1)).getBikeThumbnails();
    }

    @Test
    void testGetBikeDetails() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content()
                        .json(
                                "{\"bikeId\":\"1\", " +
                                "\"fullImageUrl\":\"https://dirtybikes.com/images/large/trekticket20.png\", " +
                                "\"bikeDescription\":\"What a bouncer\", " +
                                "\"title\":\"title\", " +
                                "\"comments\":[]}"));

        verify(bikeService, times(1)).getBikeDetails(BIKE_ID);
    }

    @Test
    void testGetBikeDetails_givenWrongVerb() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.head(DETAIL_URL)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.post(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        verify(bikeService, never()).getBikeDetails(BIKE_ID);
    }

    @Test
    void testGetImage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(IMAGES_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_JPEG_VALUE));

        verify(bikeService, times(1)).getImage(IMAGE_PATH);
    }

    @Test
    void testGetImage_whenImageDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(IMAGES_URL_PREFIX + MADEUP_IMAGE_NAME)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(bikeService, times(1)).getImage(IMAGES_PREFIX + MADEUP_IMAGE_NAME);
    }

    @Test
    void testGetImage_givenWrongVerb() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.head(IMAGES_URL)
//                        .contentType(MediaType.IMAGE_JPEG_VALUE)
//                        .accept(MediaType.IMAGE_JPEG_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(IMAGES_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(IMAGES_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(IMAGES_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.post(IMAGES_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        verify(bikeService, never()).getImage(IMAGE_PATH);
    }

    @Test
    void testGetThumbnailImage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_IMAGE_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_JPEG_VALUE));

        verify(bikeService, times(1)).getImage(THUMBNAIL_PATH);
    }

    @Test
    void testGetThumbnailImage_whenImageDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_IMAGE_URL_PREFIX + MADEUP_IMAGE_NAME)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(bikeService, times(1)).getImage(THUMBNAIL_IMAGE_PREFIX + MADEUP_IMAGE_NAME);
    }

    @Test
    void testGetThumbnailImage_givenWrongVerb() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.head(THUMBNAIL_IMAGE_URL)
//                        .contentType(MediaType.IMAGE_JPEG_VALUE)
//                        .accept(MediaType.IMAGE_JPEG_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(THUMBNAIL_IMAGE_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(THUMBNAIL_IMAGE_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(THUMBNAIL_IMAGE_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.post(THUMBNAIL_IMAGE_URL)
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        verify(bikeService, never()).getImage(THUMBNAIL_PATH);
    }

    @Test
    void testPostComment() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(COMMENTS_IMAGE_URL)
                        .content(COMMENT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(bikeService, times(1)).addComment(BIKE_ID, COMMENT);
    }

    @Test
    void testPostComment() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.head(COMMENTS_IMAGE_URL)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(COMMENTS_IMAGE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(COMMENTS_IMAGE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(COMMENTS_IMAGE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.get(COMMENTS_IMAGE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        verify(bikeService, never()).addComment(BIKE_ID, "");
    }
}