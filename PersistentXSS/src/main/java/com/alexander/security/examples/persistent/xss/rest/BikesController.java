package com.alexander.security.examples.persistent.xss.rest;

import com.alexander.security.examples.persistent.xss.rest.model.BikeDetails;
import com.alexander.security.examples.persistent.xss.rest.model.BikesThumbnail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BikesController {

    @GetMapping(value = "/bikes/thumbnails",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BikesThumbnail>> getBikeThumbnails() {
        return ResponseEntity.of(Optional.ofNullable(List.of(BikesThumbnail.builder().build())));
    }

    @GetMapping(value = "/bikes/detail/{bikeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BikeDetails> getBikeDetails(@PathVariable("bikeId") String bikeId) {
        return ResponseEntity.of(Optional.ofNullable(BikeDetails.builder()
                .bikeId(bikeId).build()));
    }
}
