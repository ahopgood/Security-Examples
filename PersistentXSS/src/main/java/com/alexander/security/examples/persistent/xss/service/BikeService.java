package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.rest.model.BikesThumbnail;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;

import java.util.List;

public interface BikeService {

    BikeDetails getBikeDetails(String id);

    List<BikesThumbnail> getBikeThumbnails();
}
