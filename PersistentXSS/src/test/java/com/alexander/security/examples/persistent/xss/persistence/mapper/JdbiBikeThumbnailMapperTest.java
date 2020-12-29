package com.alexander.security.examples.persistent.xss.persistence.mapper;


import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.tables.Bikes;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JdbiBikeThumbnailMapperTest {

    private ResultSet resultSet = mock(ResultSet.class);
    private StatementContext context = mock(StatementContext.class);

    private JdbiBikeThumbnailMapper mapper = new JdbiBikeThumbnailMapper();

    @Test
    void map() throws SQLException {
        when(resultSet.getString(BikeThumbnailEntity.ID)).thenReturn("2");
        when(resultSet.getString(BikeThumbnailEntity.BIKE_ID)).thenReturn("1");
        when(resultSet.getString(BikeThumbnailEntity.URL)).thenReturn("/thumbnail/trekticket20.png");
        when(resultSet.getString(Bikes.TITLE)).thenReturn("Trek Ticket 20");

        BikeThumbnailEntity thumbnail = mapper.map(resultSet, context);

        assertThat(thumbnail.getBikeId()).isEqualTo("1");
        assertThat(thumbnail.getThumbnailImageUrl()).isEqualTo("/thumbnail/trekticket20.png");
        assertThat(thumbnail.getTitle()).isEqualTo("Trek Ticket 20");
        assertThat(thumbnail.getId()).isEqualTo("2");
    }
}