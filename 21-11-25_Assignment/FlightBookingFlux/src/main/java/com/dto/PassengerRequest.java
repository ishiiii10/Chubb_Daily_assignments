package com.dto;



import lombok.Data;

@Data
public class PassengerRequest {
    private String name;
    private Integer age;
    private String gender;
    private String seatNumber;
    private String mealType;
}