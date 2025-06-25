package com.example.ownerservice.service;


import com.example.ownerservice.DTO.OwnerCreateDTO;
import com.example.ownerservice.DTO.OwnerResponseDTO;
import com.example.ownerservice.DTO.OwnerUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerResponseDTO save(OwnerCreateDTO ownerCreateDTO);
    void deleteById(Long id);
    void deleteAll();
    OwnerResponseDTO update(Long id, OwnerUpdateDto ownerUpdateDTO);
    OwnerResponseDTO getById(Long id);
    List<OwnerResponseDTO> getAll();
    public Page<OwnerResponseDTO> getFilteredOwners(String name, LocalDate birthDate, Pageable pageable);
}
