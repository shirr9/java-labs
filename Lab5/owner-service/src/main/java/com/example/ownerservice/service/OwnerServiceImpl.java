package com.example.ownerservice.service;

import com.example.ownerservice.DTO.OwnerCreateDTO;
import com.example.ownerservice.DTO.OwnerDtoMapper;
import com.example.ownerservice.DTO.OwnerResponseDTO;
import com.example.ownerservice.DTO.OwnerUpdateDto;
import com.example.ownerservice.model.Owner;
import com.example.ownerservice.repository.OwnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerDtoMapper ownerDtoMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerDtoMapper ownerDtoMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerDtoMapper = ownerDtoMapper;
    }

    @Override
    public List<OwnerResponseDTO> getAll() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream().map(ownerDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<OwnerResponseDTO> getFilteredOwners(String name, LocalDate birthDate, Pageable pageable) {
        Page<Owner> result;
        if (birthDate != null) {
            result = ownerRepository.findByNameContainingIgnoreCaseAndBirthDate(name, birthDate, pageable);
        } else {
            result = ownerRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return result.map(ownerDtoMapper::toDto);
    }

    @Override
    public OwnerResponseDTO save(OwnerCreateDTO ownerCreateDTO) {
        Owner owner = ownerDtoMapper.toOwner(ownerCreateDTO);
        Owner saved = ownerRepository.save(owner);
        return ownerDtoMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new IllegalArgumentException("Owner with this ID doesn't exist");
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        ownerRepository.deleteAll();
    }

    @Override
    public OwnerResponseDTO update(Long id, OwnerUpdateDto ownerUpdateDTO) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isEmpty()) {
            throw new IllegalArgumentException("Owner with this ID " + id + " doesn't exist");
        }
        Owner owner = optionalOwner.get();
        ownerDtoMapper.updateOwner(ownerUpdateDTO, owner);
        return ownerDtoMapper.toDto(ownerRepository.save(owner));
    }

    @Override
    public OwnerResponseDTO getById(Long id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isEmpty()) {
            throw new IllegalArgumentException("Owner with this ID " + id + " doesn't exist");
        }
        return ownerDtoMapper.toDto(optionalOwner.get());
    }
}
