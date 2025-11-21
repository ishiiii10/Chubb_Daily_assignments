package com.entity;



import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("booking")
public class Booking {

    @Id
    private Long id;

    private String pnr;
    private Long flightId;
    private String contactEmail;

    private Integer seatsBooked;
    private Double totalPrice;
    private String status;
    
    
}