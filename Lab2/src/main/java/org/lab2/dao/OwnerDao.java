package org.lab2.dao;

import org.lab2.entity.Owner;

import java.util.List;

public interface OwnerDao {
    Owner save(Owner entity);
    void deleteById(long id);
    void deleteByEntity(Owner entity);
    void deleteAll();
    Owner update(Owner entity);
    Owner getById(long id);
    List<Owner> getAll();
}
