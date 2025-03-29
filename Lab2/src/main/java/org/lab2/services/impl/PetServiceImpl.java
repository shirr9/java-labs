package org.lab2.services.impl;

import org.lab2.dao.PetDao;
import org.lab2.entity.Pet;
import org.lab2.services.PetService;

import java.util.List;

public class PetServiceImpl implements PetService {
    private final PetDao petDao;

    public PetServiceImpl(PetDao petDao) {
        this.petDao = petDao;
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name is required");
        }
        if (pet.getOwner() == null) {
            throw new IllegalArgumentException("Pet must have an owner");
        }
        return petDao.save(pet);
    }

    @Override
    public void deleteById(long id) {
        petDao.deleteById(id);
    }

    @Override
    public void deleteByEntity(Pet pet) {
        petDao.deleteByEntity(pet);
    }

    @Override
    public void deleteAll() {
        petDao.deleteAll();
    }

    @Override
    public Pet update(Pet pet) {
        Pet existing = petDao.getById(pet.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Pet not found with id: " + pet.getId());
        }
        return petDao.save(pet);
    }

    @Override
    public Pet getById(long id) {
        return petDao.getById(id);
    }

    @Override
    public List<Pet> getAll() {
        return petDao.getAll();
    }
}
