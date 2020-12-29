package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class JdbiBikeThumbnailRepository implements BikeThumbnailRepository {

    private final Jdbi jdbi;
    private final JdbiBikeThumbnailMapper mapper;

    @Inject
    public JdbiBikeThumbnailRepository(Jdbi jdbi,
                                       JdbiBikeThumbnailMapper mapper) {
        this.jdbi = jdbi;
        this.mapper = mapper;
    }


    @Override
    public List<BikeThumbnailEntity> getBikeThumbnails() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM thumbnails LEFT JOIN bikes ON thumbnails.bike_id = bikes.id;")
                    .map(mapper)
            .list()
        );
    }
}
