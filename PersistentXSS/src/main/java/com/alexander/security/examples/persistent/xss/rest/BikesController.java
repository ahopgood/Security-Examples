package com.alexander.security.examples.persistent.xss.rest;

import com.alexander.security.examples.persistent.xss.Mapper;
import com.alexander.security.examples.persistent.xss.rest.model.BikeDetailsResponse;
import com.alexander.security.examples.persistent.xss.rest.model.BikeThumbnailResponse;
import com.alexander.security.examples.persistent.xss.service.BikeService;
import com.alexander.security.examples.persistent.xss.service.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.service.model.BikeThumbnail;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@RestController
public class BikesController {


    private final BikeService bikeService;
    private final Mapper<BikeDetailsResponse, BikeDetails> bikeDetailsMapper;
    private final Mapper<BikeThumbnailResponse, BikeThumbnail> bikeThumbnailMapper;

    @Inject
    public BikesController(BikeService bikeService,
                           Mapper<BikeDetailsResponse, BikeDetails> bikeDetailsMapper,
                           Mapper<BikeThumbnailResponse, BikeThumbnail> bikeThumbnailMapper) {
        this.bikeService = bikeService;
        this.bikeDetailsMapper = bikeDetailsMapper;
        this.bikeThumbnailMapper = bikeThumbnailMapper;
    }

    @GetMapping(value = "/bikes/thumbnails",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BikeThumbnailResponse>> getBikeThumbnails() {
        return ResponseEntity.of(Optional.of(
                bikeService.getBikeThumbnails().stream()
                    .map(bikeThumbnailMapper::map)
                    .collect(toList())));
    }

    @GetMapping(value = "/bikes/detail/{bikeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BikeDetailsResponse> getBikeDetails(@PathVariable("bikeId") String bikeId) {
        return ResponseEntity.of(Optional.ofNullable(
                bikeDetailsMapper.map(bikeService.getBikeDetails(bikeId))));
    }

    @GetMapping(value = "/bikes/images/large/{imageName:.+}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("imageName") String imageName) throws IOException {
        ClassPathResource resource = bikeService.getImage("images/large/" + imageName);
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(resource.getInputStream()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/bikes/images/small/{imageName:.+}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<InputStreamResource> getThumbnailImage(@PathVariable("imageName") String imageName) throws IOException {
        ClassPathResource resource = bikeService.getImage("images/small/" + imageName);
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(resource.getInputStream()));

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
