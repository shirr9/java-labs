package com.Lab3.controller;

import com.Lab3.DTO.pet.PetCreateDTO;
import com.Lab3.DTO.pet.PetResponseDTO;
import com.Lab3.DTO.pet.PetUpdateDTO;
import com.Lab3.service.PetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<Page<PetResponseDTO>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false, defaultValue = "") String breed,
            @RequestParam(required = false) Integer tailLength,
            @PageableDefault(size = 5, sort = "name") Pageable pageable
    ){
        Page<PetResponseDTO> pets = petService.getFilteredPets(name, breed, tailLength, birthDate, pageable);
        return ResponseEntity.ok(pets);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PetResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> save(@Valid @RequestBody PetCreateDTO petCreateDTO) {
        return ResponseEntity.ok(petService.save(petCreateDTO));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id) {
        petService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        petService.deleteAll();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PetResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PetUpdateDTO petUpdateDTO) {
        return ResponseEntity.ok(petService.update(id, petUpdateDTO));
    }
}