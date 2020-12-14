package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeDetailsEntity;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.persistence.repository.BikeDetailsRepository;
import com.alexander.security.examples.persistent.xss.persistence.repository.BikeThumbnailRepository;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeThumbnailRepository bikeThumbnailRepository;
    private final Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper;

    private final BikeDetailsRepository bikeDetailsRepository;
    private final Mapper<BikeDetails, BikeDetailsEntity> bikeDetailsMapper;

    @Inject
    public BikeServiceImpl(BikeThumbnailRepository bikeThumbnailRepository,
                           @Named("serviceBikeThumbnailMapper") Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper,
                           BikeDetailsRepository bikeDetailsRepository,
                           @Named("serviceBikeDetailsMapper") Mapper<BikeDetails, BikeDetailsEntity> bikeDetailsMapper) {
        this.bikeThumbnailRepository = bikeThumbnailRepository;
        this.bikeThumbnailMapper = bikeThumbnailMapper;
        this.bikeDetailsRepository = bikeDetailsRepository;
        this.bikeDetailsMapper = bikeDetailsMapper;
    }

    @Override
    public BikeDetails getBikeDetails(String id) {
        return bikeDetailsMapper.map(bikeDetailsRepository.getBikeDetails(id));
    }

    @Override
    public List<BikeThumbnail> getBikeThumbnails() {
        return bikeThumbnailRepository.getBikeThumbnails().stream()
                .map(bikeThumbnailMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ClassPathResource getImage(String path) {
        return new ClassPathResource(path);
    }
}
