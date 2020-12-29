package com.alexander.security.examples.persistent.xss.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Comment {
    private final String comment;
}
