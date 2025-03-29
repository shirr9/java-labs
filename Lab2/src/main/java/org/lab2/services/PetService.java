package org.lab2.services;

import org.lab2.entity.Pet;
import java.util.List;

public interface PetService {
    Pet save(Pet pet);
    void deleteById(long id);
    void deleteByEntity(Pet pet);
    void deleteAll();
    Pet update(Pet pet);
    Pet getById(long id);
    List<Pet> getAll();
}