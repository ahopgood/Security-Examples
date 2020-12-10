package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ExtendWith(SpringExtension.class)
class JdbiBikeThumbnailRepositoryTest {

    @Autowired
    private Jdbi jdbi;

    private JdbiBikeThumbnailMapper jdbiBikeThumbnailMapper = new JdbiBikeThumbnailMapper();

    private BikeThumbnailRepository repository;

    @Test
    void testGetBikeThumbnails() {
        repository = new JdbiBikeThumbnailRepository(jdbi, jdbiBikeThumbnailMapper);

        assertThat(jdbi).isNotNull();
        assertThat(repository).isNotNull();

        assertThat(repository.getBikeThumbnails()).isNotEmpty();
        BikeThumbnailEntity thumbnail = repository.getBikeThumbnails().get(0);
        assertThat(thumbnail.getId()).isEqualTo("t1");
        assertThat(thumbnail.getBikeId()).isEqualTo("b1");
        assertThat(thumbnail.getTitle()).isEqualTo("Trek Ticket 20");
        assertThat(thumbnail.getThumbnailImageUrl()).isEqualTo("thumb/trekticket20.png");
    }
}