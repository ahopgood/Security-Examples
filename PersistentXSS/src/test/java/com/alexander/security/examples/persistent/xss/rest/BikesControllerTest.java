package com.alexander.security.examples.persistent.xss.rest;

import com.alexander.security.examples.persistent.xss.rest.advice.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BikesControllerTest {

    private static final String THUMBNAIL_URL = "/bikes/thumbnails";

    private static final HttpMessageConverter MESSAGE_CONVERTER
            = new MappingJackson2HttpMessageConverter(new ObjectMapper());

    private BikesController controller = new BikesController();
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(MESSAGE_CONVERTER)
            .setControllerAdvice(ExceptionHandler.class)
            .build();

    @Test
    void testGetBikeThumbnails_givenWrongApplicationType() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_ATOM_XML))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    void testGetBikeThumbnails_givenWrongVerb() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.head(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.patch(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.put(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        mockMvc.perform(
                MockMvcRequestBuilders.post(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void testGetBikeThumbnails() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(THUMBNAIL_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"bikeId\":null, \"imageUrl\":null, \"title\":null}]"));
    }
}