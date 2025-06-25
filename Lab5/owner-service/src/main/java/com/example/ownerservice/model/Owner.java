package com.example.ownerservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Setter
@Getter
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
    @ElementCollection
    @CollectionTable(name = "owner_pet_ids", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "pet_id")
    private Set<Long> petId = new HashSet<Long>();
    @Column(name = "user_id")
    private Long userId;

    public Owner() {}

    public Owner(Long id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public void addPet(Long pet_id) {
        petId.add(pet_id);
    }

    public void removePet(Long pet_id) {
        petId.remove(pet_id);
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