package com.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.entity.Passenger;
import reactor.core.publisher.Flux;

public interface PassengerRepo extends R2dbcRepository<Passenger, Long> {
    Flux<Passenger> findByBookingId(Long bookingId);
}
