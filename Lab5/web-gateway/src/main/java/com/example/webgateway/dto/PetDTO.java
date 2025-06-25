package com.example.webgateway.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PetDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;
    private Integer tailLength;
    private Long ownerId;
    private Set<Long> friends;
}
