package com.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.entity.Booking;
import reactor.core.publisher.Mono;

public interface BookingRepo extends R2dbcRepository<Booking, Long> {
    Mono<Booking> findByPnr(String pnr);
    Mono<Boolean> existsByPnr(String pnr);
}