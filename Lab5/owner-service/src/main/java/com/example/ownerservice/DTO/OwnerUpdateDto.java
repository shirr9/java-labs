package com.example.ownerservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OwnerUpdateDto {
    private String name;
    private LocalDate birthDate;
}
