package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;

import java.util.Optional;

public interface BikeDetailsRepository {

    Optional<BikeDetailsEntity> getBikeDetails(String bikeId);

    int addComment(String bikeId, String comment);
}
