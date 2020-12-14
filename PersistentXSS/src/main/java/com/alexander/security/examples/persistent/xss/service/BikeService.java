package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

public interface BikeService {

    BikeDetails getBikeDetails(String id);

    List<BikeThumbnail> getBikeThumbnails();

    ClassPathResource getImage(String path);
}
