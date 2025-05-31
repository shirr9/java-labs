package com.example.webgateway.controller;

import com.example.webgateway.dto.OwnerCreateDTO;
import com.example.webgateway.dto.OwnerResponseDTO;
import com.example.webgateway.dto.OwnerUpdateDto;
import com.example.webgateway.service.OwnerGatewayService;
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
public class OwnerGatewayController {

    private final OwnerGatewayService ownerGatewayService;

    public OwnerGatewayController(OwnerGatewayService ownerGatewayService) {
        this.ownerGatewayService = ownerGatewayService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<OwnerResponseDTO>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @PageableDefault(size = 5, sort = "name") Pageable pageable) throws Exception {
        Page<OwnerResponseDTO> result = ownerGatewayService.getFilteredOwners(name, birthDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ownerGatewayService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> save(@RequestBody OwnerCreateDTO ownerCreateDTO) throws Exception {
        return ResponseEntity.ok(ownerGatewayService.save(ownerCreateDTO));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public void deleteById(@PathVariable Long id) throws Exception {
        ownerGatewayService.deleteById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() throws Exception {
        ownerGatewayService.deleteAll();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public ResponseEntity<OwnerResponseDTO> update(@PathVariable Long id, @RequestBody OwnerUpdateDto ownerUpdateDTO) throws Exception {
        return ResponseEntity.ok(ownerGatewayService.update(id, ownerUpdateDTO));
    }
}

