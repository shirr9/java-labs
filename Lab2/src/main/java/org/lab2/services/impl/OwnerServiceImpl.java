package org.lab2.services.impl;

import org.lab2.dao.OwnerDao;
import org.lab2.entity.Owner;
import org.lab2.services.OwnerService;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao ownerDao;

    public OwnerServiceImpl(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    @Override
    public Owner save(Owner owner) {
        if (owner.getName() == null || owner.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        return ownerDao.save(owner);
    }

    @Override
    public void deleteById(long id) {
        ownerDao.deleteById(id);
    }

    @Override
    public void deleteByEntity(Owner owner) {
        ownerDao.deleteByEntity(owner);
    }

    @Override
    public void deleteAll() {
        ownerDao.deleteAll();
    }

    @Override
    public Owner update(Owner owner) {
        Owner existing = ownerDao.getById(owner.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Owner not found with id: " + owner.getId());
        }
        return ownerDao.save(owner);
    }

    @Override
    public Owner getById(long id) {
        return ownerDao.getById(id);
    }

    @Override
    public List<Owner> getAll() {
        return ownerDao.getAll();
    }
}
