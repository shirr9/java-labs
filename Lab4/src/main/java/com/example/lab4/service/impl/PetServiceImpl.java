package com.example.lab4.service.impl;

import com.example.lab4.DTO.pet.PetCreateUpdateDTO;
import com.example.lab4.DTO.pet.PetDtoMapper;
import com.example.lab4.DTO.pet.PetResponseDTO;
import com.example.lab4.exception.OwnerNotFoundException;
import com.example.lab4.exception.PetsNotFoundException;
import com.example.lab4.models.Owner;
import com.example.lab4.models.Pet;
import com.example.lab4.repository.OwnerRepository;
import com.example.lab4.repository.PetRepository;
import com.example.lab4.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final PetDtoMapper petDtoMapper;

    public PetServiceImpl(PetRepository petRepository, OwnerRepository ownerRepository, PetDtoMapper petDtoMapper) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.petDtoMapper = petDtoMapper;
    }

    private Owner findOwnerOrThrow(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    private Set<Pet> findFriendsOrThrow(Set<Long> friendsIds) {
        List<Pet> petsFromDb = petRepository.findAllById(friendsIds);

        Set<Long> foundIds = petsFromDb.stream()
                .map(Pet::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(friendsIds);
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            throw new PetsNotFoundException(missingIds);
        }

        return new HashSet<>(petsFromDb);
    }

    @Override
    public PetResponseDTO save(PetCreateUpdateDTO petCreateDTO) {
        Pet pet = petDtoMapper.toPet(petCreateDTO);

        Owner owner = findOwnerOrThrow(petCreateDTO.getOwnerId());
        pet.setOwner(owner);

        Set<Pet> friends = findFriendsOrThrow(petCreateDTO.getFriendsId());
        pet.setFriends(friends);

        petRepository.save(pet);
        return petDtoMapper.toDto(pet);
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
    public PetResponseDTO update(Long id, PetCreateUpdateDTO petUpdateDTO) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) {
            throw new IllegalArgumentException("Pet with this ID " + id + " doesn't exist");
        }
        Pet pet = optionalPet.get();
        petDtoMapper.updatePet(petUpdateDTO, pet);

        Owner owner = findOwnerOrThrow(petUpdateDTO.getOwnerId());
        pet.setOwner(owner);

        Set<Pet> friends = findFriendsOrThrow(petUpdateDTO.getFriendsId());
        pet.updateFriends(friends);

        petRepository.save(pet);
        return petDtoMapper.toDto(pet);
    }

    @Override
    public PetResponseDTO getById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) {
            throw new IllegalArgumentException("Pet with this ID " + id + " doesn't exist");
        }
        return petDtoMapper.toDto(optionalPet.get());
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
        return pets.map(petDtoMapper::toDto);
    }
}
