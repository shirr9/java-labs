package com.example.webgateway.dto;

import org.springframework.stereotype.Component;

@Component
public class OwnerDtoMapper {
    public OwnerResponseDTO toDto(OwnerDTO owner) {
        OwnerResponseDTO dto = new OwnerResponseDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        dto.setPets(owner.getPetIds());
        return dto;
    }

    public OwnerDTO toOwner(OwnerCreateDTO dto) {
        OwnerDTO owner = new OwnerDTO();
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
        owner.setUserId(dto.getUserId());
        return owner;
    }

    public void updateOwner(OwnerUpdateDto dto, OwnerDTO owner) {
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
    }
}