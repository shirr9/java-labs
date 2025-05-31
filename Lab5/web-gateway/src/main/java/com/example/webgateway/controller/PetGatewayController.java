package com.example.webgateway.controller;

import com.example.webgateway.dto.PetCreateUpdateDTO;
import com.example.webgateway.dto.PetResponseDTO;
import com.example.webgateway.service.PetGatewayService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/pets")
public class PetGatewayController {

    private final PetGatewayService petGatewayService;

    public PetGatewayController(PetGatewayService petGatewayService) {
        this.petGatewayService = petGatewayService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<PetResponseDTO>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false, defaultValue = "") String breed,
            @RequestParam(required = false) Integer tailLength,
            @PageableDefault(size = 5, sort = "name") Pageable pageable) throws Exception {
        Page<PetResponseDTO> pets = petGatewayService.getFilteredPets(name, breed, tailLength, birthDate, pageable);
        return ResponseEntity.ok(pets);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PetResponseDTO> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(petGatewayService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfPet(#dto.ownerId)")
    public ResponseEntity<PetResponseDTO> save(@RequestBody PetCreateUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(petGatewayService.save(dto));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfPet(#id)")
    public ResponseEntity<PetResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PetCreateUpdateDTO dto) throws Exception {
        return ResponseEntity.ok(petGatewayService.update(id, dto));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfPet(#id)")
    public void deleteById(@PathVariable Long id) throws Exception {
        petGatewayService.deleteById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() throws Exception {
        petGatewayService.deleteAll();
    }
}