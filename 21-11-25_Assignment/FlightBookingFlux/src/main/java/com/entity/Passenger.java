package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.enums.Gender;
import com.enums.MealType;

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
    private Gender gender;
    private String seatNumber;
    private MealType meal;
}
