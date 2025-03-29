package org.lab2.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.lab2.dao.OwnerDao;
import org.lab2.entity.Owner;
import org.lab2.util.JpaUtil;

import java.util.List;

public class OwnerDaoImpl implements OwnerDao {
    @Override
    public Owner save(Owner entity) {
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

            Owner owner = em.find(Owner.class, id);
            if (owner != null) {
                em.remove(owner);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteByEntity(Owner entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                Owner managedEntity = em.merge(entity);
                em.remove(managedEntity);
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
            em.createQuery("DELETE FROM Owner").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Owner update(Owner entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public Owner getById(long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Owner.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Owner> getAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}