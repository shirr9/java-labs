package com.Lab3.DTO.pet;

import com.Lab3.models.Color;
import com.Lab3.models.Owner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PetResponseDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;
    private Long ownerId;
    private int tailLength;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBreed() {
        return breed;
    }


    public int getTailLength() {
        return tailLength;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
