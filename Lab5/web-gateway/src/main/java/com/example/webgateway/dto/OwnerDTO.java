package com.example.webgateway.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OwnerDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Long userId;
    private List<Long> petIds;
}
