package com.example.lab4.service.impl;

import com.example.lab4.DTO.owner.OwnerCreateDTO;
import com.example.lab4.DTO.owner.OwnerDtoMapper;
import com.example.lab4.DTO.owner.OwnerResponseDTO;
import com.example.lab4.DTO.owner.OwnerUpdateDto;
import com.example.lab4.exception.UserNotFoundException;
import com.example.lab4.models.MyUser;
import com.example.lab4.models.Owner;
import com.example.lab4.repository.OwnerRepository;
import com.example.lab4.repository.UserRepository;
import com.example.lab4.service.OwnerService;
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
    private final UserRepository userRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerDtoMapper ownerDtoMapper, UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerDtoMapper = ownerDtoMapper;
        this.userRepository = userRepository;
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
        MyUser user = userRepository.findById(ownerCreateDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ownerCreateDTO.getUserId()));
        owner.setUser(user);
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
