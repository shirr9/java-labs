package com.Lab3.DTO.owner;

import com.Lab3.models.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerDtoMapper {
    public OwnerResponseDTO toDto(Owner owner) {
        OwnerResponseDTO dto =  new OwnerResponseDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }
    public Owner toOwner(OwnerCreateDTO ownerCreateDTO) {
        Owner owner = new Owner();
        owner.setName(ownerCreateDTO.getName());
        owner.setBirthDate(ownerCreateDTO.getBirthDate());
        return owner;
    }
    public void updateOwner(OwnerUpdateDTO ownerUpdateDTO, Owner owner) {
        owner.setName(ownerUpdateDTO.getName());
        owner.setBirthDate(ownerUpdateDTO.getBirthDate());
    }
}
