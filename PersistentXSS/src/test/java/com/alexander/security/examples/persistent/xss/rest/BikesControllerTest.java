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
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BikesControllerTest {

    private static final String THUMBNAIL_URL = "/bikes/thumbnails";
    private static final String BIKE_ID = "1";
    private static final String DETAIL_URL = "/bikes/detail/" + BIKE_ID;


    private static final HttpMessageConverter MESSAGE_CONVERTER
            = new MappingJackson2HttpMessageConverter(new ObjectMapper());

    private BikeService bikeService = mock(BikeService.class);
    private BikeDetailsMapper detailsMapper = new BikeDetailsMapper();
    private BikeThumbnailMapper thumbnailMapper = new BikeThumbnailMapper();

    private BikesController controller = new BikesController(bikeService, detailsMapper, thumbnailMapper);

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(MESSAGE_CONVERTER)
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
    }

    @Test
    void testGetBikeThumbnails_givenWrongApplicationType() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_URL)
                        .contentType(MediaType.APPLICATION_ATOM_XML_VALUE)
                        .accept(MediaType.APPLICATION_ATOM_XML_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());

        verify(bikeService, never()).getBikeThumbnails();
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
    void testGetBikeDetails_givenWrongApplicationType() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(DETAIL_URL)
                        .contentType(MediaType.APPLICATION_ATOM_XML_VALUE)
                        .accept(MediaType.APPLICATION_ATOM_XML_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());

        verify(bikeService, never()).getBikeDetails(BIKE_ID);
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
}