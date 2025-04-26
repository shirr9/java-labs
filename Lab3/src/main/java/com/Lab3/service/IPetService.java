package com.Lab3.service;

import com.Lab3.DTO.pet.PetCreateDTO;
import com.Lab3.DTO.pet.PetResponseDTO;
import com.Lab3.DTO.pet.PetUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IPetService {
    PetResponseDTO save(PetCreateDTO petCreateDTO);
    void deleteById(Long id);
    void deleteAll();
    PetResponseDTO update(Long id, PetUpdateDTO petUpdateDTO);
    PetResponseDTO getById(Long id);
    Page<PetResponseDTO> getFilteredPets(String name, String breed, Integer tailLength,
                                         LocalDate birthDate, Pageable pageable);
}
