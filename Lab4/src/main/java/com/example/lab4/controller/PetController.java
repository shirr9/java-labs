package com.example.lab4.controller;

import com.example.lab4.DTO.pet.PetCreateUpdateDTO;
import com.example.lab4.DTO.pet.PetResponseDTO;
import com.example.lab4.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#dto.ownerId)")
    public ResponseEntity<PetResponseDTO> save(@RequestBody PetCreateUpdateDTO dto) {
        return ResponseEntity.ok(petService.save(dto));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfPet(#id)")
    public ResponseEntity<PetResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PetCreateUpdateDTO dto) {
        return ResponseEntity.ok(petService.update(id, dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PetResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getById(id));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfPet(#id)")
    public void deleteById(@PathVariable Long id) {
        petService.deleteById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() {
        petService.deleteAll();
    }
}
