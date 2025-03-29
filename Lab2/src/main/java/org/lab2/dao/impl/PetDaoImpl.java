package org.lab2.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.lab2.dao.PetDao;
import org.lab2.entity.Pet;
import org.lab2.util.JpaUtil;

import java.util.List;

public class PetDaoImpl implements PetDao {

    @Override
    public Pet save(Pet entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Pet pet = em.find(Pet.class, id);
            if (pet != null) {
                em.remove(pet);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteByEntity(Pet entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                Pet managedPet = em.merge(entity);
                em.remove(managedPet);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Pet").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Pet update(Pet entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Pet updatedPet = em.merge(entity);
            em.getTransaction().commit();
            return updatedPet;
        } finally {
            em.close();
        }
    }

    @Override
    public Pet getById(long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Pet.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pet> getAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Pet> query = em.createQuery("SELECT p FROM Pet p", Pet.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}