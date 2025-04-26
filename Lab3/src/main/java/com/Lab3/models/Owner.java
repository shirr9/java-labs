package com.Lab3.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private Set<Pet> pets = new HashSet<>();

    public Owner() {}

    public Owner(Long id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwnerId(this.id);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setOwnerId(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Set<Pet> getPets() {
        return pets;
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

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner owner = (Owner) o;
        return Objects.equals(this.id, owner.getId()) &&
                Objects.equals(this.name, owner.getName()) &&
                Objects.equals(this.birthDate, owner.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate);
    }
}