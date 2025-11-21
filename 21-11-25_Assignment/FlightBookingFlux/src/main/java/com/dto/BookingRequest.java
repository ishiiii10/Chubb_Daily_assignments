package com.dto;



import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private String contactEmail;
    private Integer seatsToBook;
    private List<PassengerRequest> passengers;
}
