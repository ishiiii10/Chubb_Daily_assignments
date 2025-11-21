package com.service;



import com.entity.Booking;
import com.entity.Flight;
import com.entity.Passenger;
import com.repository.BookingRepo;
import com.repository.FlightRepo;
import com.repository.PassengerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepo bookingRepo;
    private final FlightRepo flightRepo;
    private final PassengerRepo passengerRepo;

    private final Random random = new Random();

    /**
     * Create booking with passengers. Returns:
     * { "pnr": "ABC123", "status": "CONFIRMED" }
     *
     * If flight not found or seats not available -> 404
     */
    public Mono<java.util.Map<String, Object>> createBooking(Long flightId,
                                                             String contactEmail,
                                                             int seatsToBook,
                                                             List<Passenger> passengers) {

        if (passengers == null || passengers.size() != seatsToBook) {
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        return flightRepo.findById(flightId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(flight -> {
                    if (flight.getAvailableSeats() == null || flight.getAvailableSeats() < seatsToBook) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
                    }

                    double totalPrice = seatsToBook * (flight.getBaseFare() == null ? 0.0 : flight.getBaseFare());

                    // create booking entity (PNR generation)
                    return (Mono<? extends Map<String, Object>>) generateUniquePnr()
                            .flatMap(pnr -> {
                                Booking booking = Booking.builder()
                                        .pnr(pnr)
                                        .flightId(flight.getId())
                                        .contactEmail(contactEmail)
                                        .seatsBooked(seatsToBook)
                                        .totalPrice(totalPrice)
                                        .status("CONFIRMED")
                                        .build();

                                // reduce seats and save flight, then save booking and passengers
                                flight.setAvailableSeats(flight.getAvailableSeats() - seatsToBook);

                                return flightRepo.save(flight)
                                        .then(bookingRepo.save(booking))
                                        .flatMap(savedBooking -> {
                                            // save passengers, attach bookingId
                                            return Flux.fromIterable(passengers)
                                                    .flatMap(p -> {
                                                        p.setBookingId(savedBooking.getId());
                                                        return passengerRepo.save(p);
                                                    })
                                                    .then(Mono.just(Collections.<String, Object>unmodifiableMap(
                                                            java.util.Map.of("pnr", savedBooking.getPnr(), "status", savedBooking.getStatus())
                                                    )));
                                        });
                            });
                });
    }

    /**
     * Get booking minimal info by PNR:
     * { "pnr": "...", "status": "...", "flightCode": "..." }
     * Returns 404 if not found.
     */
    public Mono<java.util.Map<String, Object>> getTicketByPnr(String pnr) {
        return bookingRepo.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(booking -> flightRepo.findById(booking.getFlightId())
                        .defaultIfEmpty(null)
                        .map(flight -> {
                            if (flight == null) {
                                // If flight is missing, we still return booking minimal but to follow rule, return 404
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                            }
                            return java.util.Map.<String, Object>of(
                                    "pnr", booking.getPnr(),
                                    "status", booking.getStatus(),
                                    "flightCode", flight.getFlightCode()
                            );
                        }));
    }

    /**
     * Cancel booking by PNR. Business rule: cannot cancel within 24 hours of flight departure.
     * If booking not found OR cannot cancel OR already cancelled -> return 404
     */
    public Mono<java.util.Map<String, Object>> cancelBooking(String pnr) {
        return bookingRepo.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(booking -> {
                    if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
                    }

                    return flightRepo.findById(booking.getFlightId())
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                            .flatMap(flight -> {
                                LocalDateTime now = LocalDateTime.now();
                                if (!flight.getDepartureTime().isAfter(now.plusHours(24))) {
                                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
                                }

                                // restore seats
                                flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatsBooked());
                                booking.setStatus("CANCELLED");

                                return flightRepo.save(flight)
                                        .then(bookingRepo.save(booking))
                                        .map(saved -> java.util.Map.<String, Object>of(
                                                "pnr", saved.getPnr(),
                                                "status", saved.getStatus()
                                        ));
                            });
                });
    }

    // helper: generate unique 6-char alphanumeric PNR
    private Mono<String> generateUniquePnr() {
        String pnr = randomPnr();
        return bookingRepo.existsByPnr(pnr)
                .flatMap(exists -> exists ? generateUniquePnr() : Mono.just(pnr));
    }

    private String randomPnr() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int idx = random.nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
}