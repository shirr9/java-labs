package com.Lab3.DTO.owner;

import java.time.LocalDate;


public class OwnerResponseDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
