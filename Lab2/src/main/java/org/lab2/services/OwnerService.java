package org.lab2.services;

import org.lab2.entity.Owner;
import java.util.List;

public interface OwnerService {
    Owner save(Owner owner);
    void deleteById(long id);
    void deleteByEntity(Owner owner);
    void deleteAll();
    Owner update(Owner owner);
    Owner getById(long id);
    List<Owner> getAll();
}
