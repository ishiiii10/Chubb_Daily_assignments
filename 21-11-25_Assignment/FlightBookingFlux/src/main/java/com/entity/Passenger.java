package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("passenger")
public class Passenger {

    @Id
    private Long id;

    private Long bookingId;
    private String name;
    private Integer age;
    private String gender;
    private String seatNumber;
    private String meal;
}
