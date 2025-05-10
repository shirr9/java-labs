package com.example.lab4.DTO.pet;

import java.time.LocalDate;
import java.util.Set;


public class PetCreateUpdateDTO {
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;
    private Long ownerId;
    private int tailLength;
    private Set<Long> friendsId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTailLength() {
        return tailLength;
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

    public Set<Long> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Set<Long> friendsId) {
        this.friendsId = friendsId;
    }
}
