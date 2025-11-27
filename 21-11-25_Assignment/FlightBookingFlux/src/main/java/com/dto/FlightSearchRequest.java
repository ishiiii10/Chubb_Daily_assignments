package com.dto;



import lombok.Data;
import java.time.LocalDate;

import com.enums.TripType;

@Data
public class FlightSearchRequest {
    private String fromAirport;
    private String toAirport;
    // travelDate as ISO string in JSON; Jackson will map to LocalDate if configured
    private LocalDate travelDate;
    private Integer passengers;
    private TripType tripType; // optional: ONE_WAY or ROUND_TRIP (we use ONE_WAY only here)
    private LocalDate returnDate; // optional
}