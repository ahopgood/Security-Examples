package com.alexander.security.examples.persistent.xss.persistence.mapper;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.tables.Bikes;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbiBikeThumbnailMapper implements RowMapper<BikeThumbnailEntity> {

    @Override
    public BikeThumbnailEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
        return BikeThumbnailEntity.builder()
                .bikeId(rs.getString(BikeThumbnailEntity.BIKE_ID))
                .id(rs.getString(BikeThumbnailEntity.ID))
                .title(rs.getString(Bikes.TITLE))
                .thumbnailImageUrl(rs.getString(BikeThumbnailEntity.URL))
        .build();

    }
}
