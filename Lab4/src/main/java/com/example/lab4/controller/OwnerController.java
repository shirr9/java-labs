package com.example.lab4.controller;

import com.example.lab4.DTO.owner.OwnerCreateDTO;
import com.example.lab4.DTO.owner.OwnerResponseDTO;
import com.example.lab4.DTO.owner.OwnerUpdateDto;
import com.example.lab4.service.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<OwnerResponseDTO>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @PageableDefault(size = 5, sort = "name") Pageable pageable
    ) {
        Page<OwnerResponseDTO> result = ownerService.getFilteredOwners(name, birthDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> save(@RequestBody OwnerCreateDTO ownerCreateDTO) {
        return ResponseEntity.ok(ownerService.save(ownerCreateDTO));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public void deleteById(@PathVariable Long id) {
        ownerService.deleteById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() {
        ownerService.deleteAll();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public ResponseEntity<OwnerResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OwnerUpdateDto ownerUpdateDTO) {
        return ResponseEntity.ok(ownerService.update(id, ownerUpdateDTO));
    }
}