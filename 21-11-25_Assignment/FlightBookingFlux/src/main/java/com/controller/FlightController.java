package com.controller;

import com.entity.Flight;
import com.dto.FlightSearchRequest;
import com.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    // Add inventory -> { "flightId": 1 }
    @PostMapping("/airline/inventory/add")
    public Mono<Map<String, Object>> createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    // Search -> returns minimal flight json array OR 404 ({"error":"NOT_FOUND"})
    @PostMapping("/search")
    public Flux<Map<String, Object>> searchFlights(@RequestBody FlightSearchRequest request) {
        // accept LocalDate travelDate in request mapping; if not present, handler throws 404 via service
        LocalDate date = request.getTravelDate();
        int passengers = request.getPassengers() == null ? 1 : request.getPassengers();
        return flightService.searchFlights(request.getFromAirport(), request.getToAirport(), date, passengers);
    }
}