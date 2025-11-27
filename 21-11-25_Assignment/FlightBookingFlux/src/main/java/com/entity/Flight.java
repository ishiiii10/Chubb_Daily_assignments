package com.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.enums.FlightStatus;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("flight")
public class Flight {

    @Id
    private Long id;

    private Long airlineId;
    private String flightCode;
    private String fromAirport;
    private String toAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer totalSeats;
    private Integer availableSeats;
    private Double baseFare;
    private FlightStatus status;
}