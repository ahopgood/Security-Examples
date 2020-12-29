package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;

public interface BikeDetailsRepository {

    BikeDetailsEntity getBikeDetails(String bikeId);

    int addComment(String bikeId, String comment);
}
