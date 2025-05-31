package com.example.petservice.repository;
import com.example.petservice.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findByNameContainingIgnoreCaseAndBreedContainingIgnoreCase(String name, String breed, Pageable pageable);

    Page<Pet> findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndBirthDate(
            String name, String breed, LocalDate birthDate, Pageable pageable);

    Page<Pet> findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndTailLength(
            String name, String breed, int tailLength, Pageable pageable);

    Page<Pet> findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndTailLengthAndBirthDate(
            String name, String breed, int tailLength, LocalDate birthDate, Pageable pageable);
}
