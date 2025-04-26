package com.Lab3.service;

import com.Lab3.DTO.owner.OwnerCreateDTO;
import com.Lab3.DTO.owner.OwnerResponseDTO;
import com.Lab3.DTO.owner.OwnerUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IOwnerService {
    OwnerResponseDTO save(OwnerCreateDTO ownerCreateDTO);
    void deleteById(Long id);
    void deleteAll();
    OwnerResponseDTO update(Long id, OwnerUpdateDTO ownerUpdateDTO);
    OwnerResponseDTO getById(Long id);
    List<OwnerResponseDTO> getAll();
    public Page<OwnerResponseDTO> getFilteredOwners(String name, LocalDate birthDate, Pageable pageable);
}
