package com.service;



import com.entity.Airline;

import com.repository.AirlineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;



@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepo airlineRepo;

    /**
     * Create an airline and return minimal response:
     * { "airlineId": 1 }
     */
    public Mono<Object> createAirline(Airline payload) {
        // If code uniqueness must be enforced, check here
        return airlineRepo.findByCode(payload.getCode())
                .flatMap(existing -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .switchIfEmpty(
                        airlineRepo.save(payload)
                                .map(saved -> Collections.singletonMap("airlineId", saved.getId()))
                );
    }

    /**
     * Find airline by id (used by other services). Returns 404 if not found.
     */
    public Mono<Airline> getAirlineById(Long id) {
        return airlineRepo.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
