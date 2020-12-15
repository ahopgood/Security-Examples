package com.alexander.security.examples.persistent.xss;

import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.persistence.mapper.JdbiBikeThumbnailMapper;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeDetailsMapper;
import com.alexander.security.examples.persistent.xss.service.mapper.BikeThumbnailMapper;
import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.inject.Inject;
import javax.sql.DataSource;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Inject
    @Bean
    public Jdbi jdbi(DataSource dataSource) {
       return Jdbi.create(dataSource);
    }

    @Bean
    public JdbiBikeThumbnailMapper jdbiBikeThumbnailMapper() {
        return new JdbiBikeThumbnailMapper();
    }

    @Bean
    public JdbiBikeDetailsMapper jdbiBikeDetailsMapper() { return new JdbiBikeDetailsMapper(); }

    @Bean
    public BikeDetailsMapper serviceBikeDetailsMapper() {
        return new BikeDetailsMapper();
    }

    @Bean
    public BikeThumbnailMapper serviceBikeThumbnailMapper() {
        return new BikeThumbnailMapper();
    }
}
