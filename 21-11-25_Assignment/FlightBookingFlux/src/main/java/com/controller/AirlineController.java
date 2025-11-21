package com.controller;

import com.entity.Airline;
import com.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    // create airline -> { "airlineId": 1 }
    @PostMapping
    public Mono<Object> createAirline(@RequestBody Airline payload) {
        return airlineService.createAirline(payload);
    }
}