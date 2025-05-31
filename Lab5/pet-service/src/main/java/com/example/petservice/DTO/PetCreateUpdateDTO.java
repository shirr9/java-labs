package com.example.petservice.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PetCreateUpdateDTO {
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;
    private Long ownerId;
    private int tailLength;
    private Set<Long> friendsId;
}
