package com.alexander.security.examples.persistent.xss.persistence.repository;

import com.alexander.security.examples.persistent.xss.persistence.model.BikeThumbnailEntity;

import java.util.List;

public interface BikeThumbnailRepository {

    List<BikeThumbnailEntity> getBikeThumbnails();
}
