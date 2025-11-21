package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("airline")
public class Airline {

    @Id
    private Long id;

    private String code;
    private String name;
}
