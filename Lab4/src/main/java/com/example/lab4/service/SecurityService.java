package com.example.lab4.service;

import com.example.lab4.exception.OwnerNotFoundException;
import com.example.lab4.models.Owner;
import com.example.lab4.models.Pet;
import com.example.lab4.repository.OwnerRepository;
import com.example.lab4.repository.PetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final OwnerRepository ownerRepository;
    private final PetRepository  petRepository;

    public SecurityService(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    public boolean isOwner(Long ownerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Owner owner = ownerRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return owner.getId().equals(ownerId);
    }

    public boolean isOwnerOfPet(Long petId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Owner owner = ownerRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return pet.getOwner().getId().equals(owner.getId());
    }
}
