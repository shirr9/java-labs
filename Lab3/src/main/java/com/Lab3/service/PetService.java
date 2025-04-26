package com.Lab3.service;

import com.Lab3.DTO.owner.OwnerCreateDTO;
import com.Lab3.DTO.owner.OwnerResponseDTO;
import com.Lab3.DTO.pet.PetCreateDTO;
import com.Lab3.DTO.pet.PetMapper;
import com.Lab3.DTO.pet.PetResponseDTO;
import com.Lab3.DTO.pet.PetUpdateDTO;
import com.Lab3.models.Owner;
import com.Lab3.models.Pet;
import com.Lab3.repository.OwnerRepository;
import com.Lab3.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class PetService implements IPetService{
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.petMapper = petMapper;
    }

    @Override
    public Page<PetResponseDTO> getFilteredPets(String name, String breed, Integer tailLength,
                                                LocalDate birthDate, Pageable pageable) {
        Page<Pet> pets;
        if (tailLength != null && birthDate != null) {
            pets = petRepository.findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndTailLengthAndBirthDate(
                    name, breed, tailLength, birthDate, pageable);
        } else if (tailLength != null) {
            pets = petRepository.findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndTailLength(
                    name, breed, tailLength, pageable);
        } else if (birthDate != null) {
            pets = petRepository.findByNameContainingIgnoreCaseAndBreedContainingIgnoreCaseAndBirthDate(
                    name, breed, birthDate, pageable);
        } else {
            pets = petRepository.findByNameContainingIgnoreCaseAndBreedContainingIgnoreCase(
                    name, breed, pageable);
        }
        return pets.map(petMapper::toDto);
    }

    @Override
    public PetResponseDTO save(PetCreateDTO petCreateDTO) {
        Long ownerId = petCreateDTO.getOwnerId();
        if (ownerId != null && !ownerRepository.existsById(ownerId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Owner with ID " + ownerId + " does not exist");
        }
        Pet saved = petRepository.save(petMapper.toPet(petCreateDTO));
        return petMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet with this ID " + id + " doesn't exist");
        }
        petRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
    }

    @Override
    public PetResponseDTO update(Long id, PetUpdateDTO petUpdateDTO) {
//        Optional<Pet> optionalPet = petRepository.findById(id);
//        if (optionalPet.isEmpty()) {
//            throw new IllegalArgumentException("Pet with this ID " + id + " doesn't exist");
//        }
//        Pet pet = optionalPet.get();
//        petMapper.updatePet(petUpdateDTO, pet);
//        return petMapper.toDto(petRepository.save(pet));
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pet with ID " + id + " does not exist");
        }
        Long ownerId = petUpdateDTO.getOwnerId();
        if (ownerId != null && !ownerRepository.existsById(ownerId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Owner with ID " + ownerId + " does not exist");
        }
        Pet pet = optionalPet.get();
        petMapper.updatePet(petUpdateDTO, pet);
        return petMapper.toDto(petRepository.save(pet));
    }

    @Override
    public PetResponseDTO getById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) {
            throw new IllegalArgumentException("Pet with this ID " + id + " doesn't exist");
        }
        return petMapper.toDto(optionalPet.get());
    }
}