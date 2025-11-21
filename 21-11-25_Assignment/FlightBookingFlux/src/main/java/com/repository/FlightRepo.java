package com.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.entity.Flight;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

public interface FlightRepo extends R2dbcRepository<Flight, Long> {

    Flux<Flight> findByFromAirportAndToAirportAndDepartureTimeBetween(
            String from,
            String to,
            LocalDateTime start,
            LocalDateTime end
    );
}
