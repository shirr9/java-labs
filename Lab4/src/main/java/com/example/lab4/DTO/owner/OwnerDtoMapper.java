package com.example.lab4.DTO.owner;

import com.example.lab4.models.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerDtoMapper {
    public OwnerResponseDTO toDto(Owner owner) {
        OwnerResponseDTO dto =  new OwnerResponseDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        dto.setPets(owner.getPets().stream().toList());
        return dto;
    }
    public Owner toOwner(OwnerCreateDTO dto) {
        Owner owner = new Owner();
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
        //service
        return owner;
    }
    public void updateOwner(OwnerUpdateDto dto, Owner owner) {
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
    }
}
