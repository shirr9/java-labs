package com.example.webgateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OwnerCreateDTO {
    private String name;
    private LocalDate birthDate;
    private Long userId;
}
