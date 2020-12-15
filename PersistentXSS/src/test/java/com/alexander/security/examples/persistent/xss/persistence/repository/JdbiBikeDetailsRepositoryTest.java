package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ExtendWith(SpringExtension.class)
class JdbiBikeDetailsRepositoryTest {

    @Autowired
    private Jdbi jdbi;

    private JdbiBikeDetailsMapper jdbiBikeDetailsMapper = new JdbiBikeDetailsMapper();

    private JdbiBikeDetailsRepository bikeDetailsRepository;

    @Test
    void testGetBikeDetails() {
        bikeDetailsRepository = new JdbiBikeDetailsRepository(jdbi, jdbiBikeDetailsMapper);
        assertThat(jdbi).isNotNull();
        assertThat(bikeDetailsRepository).isNotNull();

//        Optional<BikeDetailsEntity> detailsOpt = bikeDetailsRepository.getBikeDetails("b1");
//        assertThat(detailsOpt.isPresent()).isTrue();

//        BikeDetailsEntity details = detailsOpt.get();
        BikeDetailsEntity details = bikeDetailsRepository.getBikeDetails("b1");
        assertThat(details.getBikeId()).isEqualTo("b1");
        assertThat(details.getDescription()).isEqualTo("A hard tail bike perfect for trails");
        assertThat(details.getUrl()).isEqualTo("bikes/images/large/trekticket20.jpg");
        assertThat(details.getTitle()).isEqualTo("Trek Ticket 20");
        assertThat(details.getComments()).hasSize(2);
        assertThat(details.getComments().get(0).getId()).isEqualTo("c11");
        assertThat(details.getComments().get(0).getComment()).isEqualTo("I rode this bike until it broke");
        assertThat(details.getComments().get(1).getId()).isEqualTo("c12");
        assertThat(details.getComments().get(1).getComment()).isEqualTo("I bought this for my paper round");
    }

    @Test
    void testGetBikeDetails_bySpecificId() {
        bikeDetailsRepository = new JdbiBikeDetailsRepository(jdbi, jdbiBikeDetailsMapper);
        assertThat(jdbi).isNotNull();
        assertThat(bikeDetailsRepository).isNotNull();

//        Optional<BikeDetailsEntity> detailsOpt = bikeDetailsRepository.getBikeDetails("b1");
//        assertThat(detailsOpt.isPresent()).isTrue();

//        BikeDetailsEntity details = detailsOpt.get();
        BikeDetailsEntity details = bikeDetailsRepository.getBikeDetails("b2");
        assertThat(details.getBikeId()).isEqualTo("b2");
//        assertThat(details.getDescription()).isEqualTo("A hard tail bike perfect for trails");
//        assertThat(details.getUrl()).isEqualTo("bikes/images/large/trekticket20.png");
//        assertThat(details.getTitle()).isEqualTo("Trek Ticket 20");
        assertThat(details.getComments()).hasSize(1);
        assertThat(details.getComments().get(0).getId()).isEqualTo("c21");
        assertThat(details.getComments().get(0).getComment()).isEqualTo("This was one of the first bikes that was stolen from me");
    }
}