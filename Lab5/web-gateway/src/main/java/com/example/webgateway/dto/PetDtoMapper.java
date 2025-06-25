package com.example.webgateway.dto;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PetDtoMapper {
    public PetResponseDTO toDto(PetDTO pet) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwnerId());

        Set<Long> petIds = Optional.ofNullable(pet.getFriends())
                .orElse(Collections.emptySet())
                .stream()
                .collect(Collectors.toSet());

        dto.setFriendsId(petIds);
        return dto;
    }

    public PetDTO toPet(PetCreateUpdateDTO dto) {
        PetDTO pet = new PetDTO();
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setTailLength(dto.getTailLength());
        pet.setOwnerId(dto.getOwnerId());
        return pet;
    }

    public void updatePet(PetCreateUpdateDTO dto, PetDTO pet) {
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setTailLength(dto.getTailLength());
    }
}
