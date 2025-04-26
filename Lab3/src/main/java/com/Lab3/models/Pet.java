package com.Lab3.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "tail_length", nullable = false)
    private int tailLength;

    @ManyToMany
    @JoinTable(
            name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Pet> friends = new HashSet<>();

    public Pet() {}

//    public Pet(String name, LocalDate birthDate, String breed, Color color) {
//        this.name = name;
//        this.birthDate = birthDate;
//        this.breed = breed;
//        this.color = color;
//    }
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }


    public Set<Pet> getFriends() {
        return friends;
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


    public void setFriends(Set<Pet> friends) {
        this.friends = friends;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
