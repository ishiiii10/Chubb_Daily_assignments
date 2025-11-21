package com.service;

import com.entity.Flight;
import com.repository.FlightRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepo flightRepo;

    /**
     * Create flight inventory. Return minimal:
     * { "flightId": 1 }
     */
    public Mono<Map<String, Object>> createFlight(Flight flight) {
        if (flight == null
                || flight.getFromAirport() == null
                || flight.getToAirport() == null
                || flight.getFromAirport().equalsIgnoreCase(flight.getToAirport())) {
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        if (flight.getDepartureTime() == null
                || flight.getArrivalTime() == null
                || !flight.getArrivalTime().isAfter(flight.getDepartureTime())) {
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        if (flight.getTotalSeats() == null || flight.getTotalSeats() <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        flight.setAvailableSeats(flight.getTotalSeats());
        return flightRepo.save(flight)
                .map(saved -> Collections.singletonMap("flightId", saved.getId()));
    }

    /**
     * Search flights for a date and return minimal JSON per flight.
     * If no flights found -> return Flux.error(404)
     */
    public Flux<Map<String, Object>> searchFlights(String from, String to, LocalDate date, int passengers) {
        // basic validations
        if (from == null || to == null || from.trim().isEmpty() || to.trim().isEmpty()) {
            return Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        if (from.equalsIgnoreCase(to)) {
            return Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        if (date == null || passengers <= 0) {
            return Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return flightRepo
                .findByFromAirportAndToAirportAndDepartureTimeBetween(
                        from.trim().toUpperCase(),
                        to.trim().toUpperCase(),
                        start,
                        end
                )
                .filter(f -> f.getAvailableSeats() != null && f.getAvailableSeats() >= passengers)
                .map(f -> Map.<String, Object>of(
                        "flightId", f.getId(),
                        "flightCode", f.getFlightCode(),
                        "from", f.getFromAirport(),
                        "to", f.getToAirport(),
                        "departure", f.getDepartureTime(),
                        "price", f.getBaseFare()
                ))
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * Convenience: get Flight by id or 404
     */
    public Mono<Flight> getFlightById(Long id) {
        return flightRepo.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * Save flight (used to update availableSeats)
     */
    public Mono<Flight> saveFlight(Flight f) {
        return flightRepo.save(f);
    }
}