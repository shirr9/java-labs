package com.Lab3.DTO.pet;

import com.Lab3.models.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    public PetResponseDTO toDto(Pet pet) {
        PetResponseDTO dto =  new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwnerId());
        dto.setTailLength(pet.getTailLength());
        return dto;
    }
    public Pet toPet(PetCreateDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setTailLength(dto.getTailLength());
        if (dto.getOwnerId() != null) {
            pet.setOwnerId(dto.getOwnerId());
        }
        return pet;
    }
    public void updatePet(PetUpdateDTO dto, Pet pet) {
        if (dto.getName() != null) pet.setName(dto.getName());
        if (dto.getBirthDate() != null) pet.setBirthDate(dto.getBirthDate());
        if (dto.getBreed() != null) pet.setBreed(dto.getBreed());
        if (dto.getColor() != null) pet.setColor(dto.getColor());
        if (dto.getOwnerId() != null) pet.setOwnerId(dto.getOwnerId());
        if (dto.getTailLength() != null) pet.setTailLength(dto.getTailLength());
    }
}
