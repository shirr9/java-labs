package com.example.webgateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class OwnerResponseDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<Long> pets;
}
