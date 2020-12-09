package com.alexander.security.examples.persistent.xss.service;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;
import com.alexander.security.examples.persistent.xss.persistence.repository.BikeThumbnailRepository;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeThumbnailRepository bikeThumbnailRepository;
    private final Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper;

    @Inject
    public BikeServiceImpl(BikeThumbnailRepository bikeThumbnailRepository,
                           Mapper<BikeThumbnail, BikeThumbnailEntity> bikeThumbnailMapper) {
        this.bikeThumbnailRepository = bikeThumbnailRepository;
        this.bikeThumbnailMapper = bikeThumbnailMapper;
    }

    @Override
    public BikeDetails getBikeDetails(String id) {
        return null;
    }

    @Override
    public List<BikeThumbnail> getBikeThumbnails() {
        return bikeThumbnailRepository.getBikeThumbnails().stream()
                .map(bikeThumbnailMapper::map)
                .collect(Collectors.toList());
    }
}
