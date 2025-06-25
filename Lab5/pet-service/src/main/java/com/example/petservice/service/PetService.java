package com.example.petservice.service;



import com.example.petservice.DTO.PetResponseDTO;
import com.example.petservice.DTO.PetCreateUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PetService {
    PetResponseDTO save(PetCreateUpdateDTO petCreateDTO);
    void deleteById(Long id);
    void deleteAll();
    PetResponseDTO update(Long id, PetCreateUpdateDTO petUpdateDTO);
    PetResponseDTO getById(Long id);
    Page<PetResponseDTO> getFilteredPets(String name, String breed, Integer tailLength,
                                         LocalDate birthDate, Pageable pageable);
}
