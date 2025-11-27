package com.dto;



import com.enums.Gender;
import com.enums.MealType;

import lombok.Data;

@Data
public class PassengerRequest {
    private String name;
    private Integer age;
    private Gender gender;
    private String seatNumber;
    private MealType mealType;
}