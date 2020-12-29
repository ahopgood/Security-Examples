package com.alexander.security.examples.persistent.xss.persistence.mapper;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.CommentEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.tables.Bikes;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbiBikeDetailsMapper implements RowMapper<BikeDetailsEntity> {

    @Override
    public BikeDetailsEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
        BikeDetailsEntity.BikeDetailsEntityBuilder builder = BikeDetailsEntity.builder()
                .description(rs.getString(BikeDetailsEntity.DESCRIPTION))
                .url(rs.getString(BikeDetailsEntity.URL))
                .bikeId(rs.getString(BikeDetailsEntity.BIKE_ID))
                .title(rs.getString(Bikes.TITLE));

        List<CommentEntity> comments = new LinkedList<>();
        comments.add(CommentEntity.builder()
                .comment(rs.getString(CommentEntity.COMMENT))
                .id(rs.getString(BikeDetailsEntity.COMMENT_ID))
                .build());
        while (rs.next()) {
            comments.add(CommentEntity.builder()
                    .comment(rs.getString(CommentEntity.COMMENT))
                    .id(rs.getNString(BikeDetailsEntity.COMMENT_ID))
            .build());
        }
        builder.comments(comments);
        return builder.build();
    }
}
