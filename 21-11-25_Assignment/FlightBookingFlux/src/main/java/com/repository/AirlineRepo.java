package com.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.entity.Airline;
import reactor.core.publisher.Mono;

public interface AirlineRepo extends R2dbcRepository<Airline, Long> {
    Mono<Airline> findByCode(String code);
}
