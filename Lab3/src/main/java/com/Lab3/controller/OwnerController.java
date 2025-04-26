package com.Lab3.controller;

import com.Lab3.DTO.owner.OwnerCreateDTO;
import com.Lab3.DTO.owner.OwnerResponseDTO;
import com.Lab3.DTO.owner.OwnerUpdateDTO;
import com.Lab3.service.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<OwnerResponseDTO>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @PageableDefault(size = 5, sort = "name") Pageable pageable
    ) {
        Page<OwnerResponseDTO> result = ownerService.getFilteredOwners(name, birthDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OwnerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<OwnerResponseDTO> save(@RequestBody OwnerCreateDTO ownerCreateDTO) {
        return ResponseEntity.ok(ownerService.save(ownerCreateDTO));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id) {
        ownerService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        ownerService.deleteAll();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<OwnerResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OwnerUpdateDTO ownerUpdateDTO) {
        return ResponseEntity.ok(ownerService.update(id, ownerUpdateDTO));
    }
}