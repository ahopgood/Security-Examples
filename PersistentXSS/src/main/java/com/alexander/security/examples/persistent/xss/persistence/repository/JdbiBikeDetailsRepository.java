package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class JdbiBikeDetailsRepository implements BikeDetailsRepository {

    private final Jdbi jdbi;
    private final JdbiBikeDetailsMapper jdbiBikeDetailsMapper;

    @Inject
    public JdbiBikeDetailsRepository(Jdbi jdbi, JdbiBikeDetailsMapper jdbiBikeDetailsMapper) {
        this.jdbi = jdbi;
        this.jdbiBikeDetailsMapper = jdbiBikeDetailsMapper;
    }

    @Override
    public BikeDetailsEntity getBikeDetails(String bikeId) {
        return jdbi.withHandle( handle ->
                handle.createQuery("SELECT bike_details.id, bike_details.bike_id, bike_details.url, bike_details.description, bikes.title, comments.id as comment_id, comments.comment " +
                        "FROM bike_details " +
                        "LEFT JOIN bikes ON bike_details.bike_id = bikes.id " +
                        "LEFT JOIN comments ON comments.bike_id = bikes.id " +
                        "WHERE bikes.id = :bikeId;")
                        .bind("bikeId", bikeId)
                .map(jdbiBikeDetailsMapper)
                        .findOne().get()
        );
    }

    @Override
    public boolean addComment(String bikeId, String comment) {
        return false;
    }
}
