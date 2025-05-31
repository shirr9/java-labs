package com.example.ownerservice.repository;


import com.example.ownerservice.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    @Query("SELECT o FROM Owner o where o.user.name = :name")
//    Optional<Owner> findByUserName(@Param("name") String name);
}
