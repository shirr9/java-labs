package org.lab2.dao;

import org.lab2.entity.Pet;

import java.util.List;

public interface PetDao {
    Pet save(Pet entity);
    void deleteById(long id);
    void deleteByEntity(Pet entity);
    void deleteAll();
    Pet update(Pet entity);
    Pet getById(long id);
    List<Pet> getAll();
}
