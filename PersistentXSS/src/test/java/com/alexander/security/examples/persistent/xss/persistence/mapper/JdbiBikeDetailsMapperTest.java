package com.alexander.security.examples.persistent.xss.persistence.mapper;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.CommentEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.tables.Bikes;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JdbiBikeDetailsMapperTest {

    private final String description = "A hard tail bike perfect for trails";
    private final String title = "Trek Ticket 20";
    private final String bikeId = "12";
    private final String fullImageUrl = "large/trekticket20.png";
    private final String comment1 = "I rode this bike until it broke";
    private final String comment2 = "Bought this in the end of season sale, bargain!";
    private final String comment1Id = "c1";

    private ResultSet resultSet = mock(ResultSet.class);
    private StatementContext context = mock(StatementContext.class);

    private JdbiBikeDetailsMapper mapper = new JdbiBikeDetailsMapper();

    @Test
    void testMap() throws SQLException {
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getString(BikeDetailsEntity.DESCRIPTION)).thenReturn(description);
        when(resultSet.getString(BikeDetailsEntity.URL)).thenReturn(fullImageUrl);
        when(resultSet.getString(BikeDetailsEntity.BIKE_ID)).thenReturn(bikeId);
        when(resultSet.getString(Bikes.TITLE)).thenReturn(title);
        when(resultSet.getString(BikeDetailsEntity.DESCRIPTION)).thenReturn(description);

        when(resultSet.getString(CommentEntity.COMMENT))
                .thenReturn(comment1);
        when(resultSet.getString(BikeDetailsEntity.COMMENT_ID))
                .thenReturn(comment1Id);

        BikeDetailsEntity details = mapper.map(resultSet, context);

        assertThat(details.getBikeId()).isEqualTo(bikeId);
        assertThat(details.getDescription()).isEqualTo(description);
        assertThat(details.getUrl()).isEqualTo(fullImageUrl);
        assertThat(details.getTitle()).isEqualTo(title);
        assertThat(details.getComments()).isNotEmpty();
        assertThat(details.getComments().get(0).getComment()).isEqualTo(comment1);
        assertThat(details.getComments().get(0).getId()).isEqualTo(comment1Id);
    }
}