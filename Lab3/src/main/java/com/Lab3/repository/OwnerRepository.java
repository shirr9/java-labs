package com.Lab3.repository;

import com.Lab3.models.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Page<Owner> findByNameContainingIgnoreCaseAndBirthDate(
            String name, LocalDate birthDate, Pageable pageable
    );
    Page<Owner> findByNameContainingIgnoreCase(
            String name, Pageable pageable
    );
}
