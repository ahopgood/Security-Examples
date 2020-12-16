package com.alexander.security.examples.persistent.xss.rest.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class CommentRequestTest {

    @Test
    void testWriteValue() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CommentRequest request = new CommentRequest("I'm a comment Morty! I'm comment Rick!");
        System.out.println(mapper.writeValueAsString(request));
    }

    private static final String COMMENT = "I'm a comment Morty, I'm comment Rick!";
    private static final String COMMENT_REQUEST = "{\"comment\":\"" + COMMENT + "\"}";

    @Test
    void testReadValue() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CommentRequest request = mapper.readValue(COMMENT_REQUEST, CommentRequest.class);
    }
}