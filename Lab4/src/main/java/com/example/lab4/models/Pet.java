package com.example.lab4.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;

    @Column(name = "tail_length", nullable = false)
    private int tailLength;

    @ManyToMany
    @JoinTable(
            name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"pet_id", "friend_id"})
    )
    @JsonIgnore
    private Set<Pet> friends = new HashSet<>();

    public Pet() {}


    public void addFriend(Pet friend) {
        if (friend == null || this.equals(friend)) {
            return;
        }
        if (!friends.contains(friend)) {
            this.friends.add(friend);
            friend.getFriends().add(this);
        }
    }

    public void removeFriend(Pet friend) {
        if (friend == null) {
            return;
        }
        this.friends.remove(friend);
        friend.getFriends().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getTailLength() {
        return tailLength;
    }

    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }

    public Set<Pet> getFriends() {
        return friends;
    }

    public void setFriends(Set<Pet> friends) {
        this.friends = friends;
        for (Pet pet : friends) {
            pet.getFriends().add(this);
        }
    }

    public void updateFriends(Set<Pet> newFriends) {
        for (Pet oldFriend : new HashSet<>(friends)) {
            oldFriend.getFriends().remove(this);
        }
        friends.clear();
        this.setFriends(newFriends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}