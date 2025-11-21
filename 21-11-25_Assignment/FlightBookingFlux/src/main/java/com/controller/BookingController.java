package com.controller;



import com.dto.BookingRequest;
import com.dto.PassengerRequest;
import com.entity.Passenger;
import com.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Create booking -> { "pnr":"XXXXXX", "status":"CONFIRMED" }
    @PostMapping("/flight/{flightId}")
    public Mono<Map<String, Object>> createBooking(
            @PathVariable Long flightId,
            @RequestBody BookingRequest dto) {

        // map PassengerRequest DTO -> entity.Passenger
        List<Passenger> passengers = (dto.getPassengers() == null) ? List.of()
                : dto.getPassengers().stream().map(pr -> Passenger.builder()
                        .name(pr.getName())
                        .age(pr.getAge())
                        .gender(pr.getGender())
                        .seatNumber(pr.getSeatNumber())
                        .meal(pr.getMealType())
                        .build()
                ).collect(Collectors.toList());

        return bookingService.createBooking(flightId, dto.getContactEmail(), dto.getSeatsToBook(), passengers);
    }

    // Get ticket by PNR -> small json { "pnr": "...", "status":"...", "flightCode":"..." }
    @GetMapping("/{pnr}")
    public Mono<Map<String, Object>> getTicket(@PathVariable String pnr) {
        return bookingService.getTicketByPnr(pnr);
    }

    // Cancel booking -> { "pnr":"...", "status":"CANCELLED" } or 404
    @DeleteMapping("/cancel/{pnr}")
    public Mono<Map<String, Object>> cancel(@PathVariable String pnr) {
        return bookingService.cancelBooking(pnr);
    }
}