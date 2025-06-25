package com.example.petservice.DTO;


import com.example.petservice.model.Pet;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PetDtoMapper {
    public PetResponseDTO toDto(Pet pet) {
        PetResponseDTO dto =  new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwnerId());
        dto.setTailLength(pet.getTailLength());
        Set<Long> petIds = Optional.ofNullable(pet.getFriends())
                .orElse(Collections.emptySet())
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toSet());
        dto.setFriendsId(petIds);
        return dto;
    }
    public Pet toPet(PetCreateUpdateDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setTailLength(dto.getTailLength());
        pet.setOwnerId(dto.getOwnerId());
        // friends in service check?
        // owner check in the upper level
        return pet;
    }
    public void updatePet(PetCreateUpdateDTO dto, Pet pet) {
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setTailLength(dto.getTailLength());
        // friends in service?
        // no update owner mb or check in the upper level
    }
}
