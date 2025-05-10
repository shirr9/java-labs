package com.example.lab4.service;

import com.example.lab4.DTO.owner.OwnerCreateDTO;
import com.example.lab4.DTO.owner.OwnerResponseDTO;
import com.example.lab4.DTO.owner.OwnerUpdateDto;
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
