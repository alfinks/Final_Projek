/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.example.demo.exceptions.NonexistentEntityException;
import com.example.demo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Maulana Alfiansyah
 */
public class FinalProjekJpaController implements Serializable {

    public FinalProjekJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_demo_jar_0.0.1-SNAPSHOTPU");
    
    public FinalProjekJpaController(){}

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FinalProjek finalProjek) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(finalProjek);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFinalProjek(finalProjek.getId()) != null) {
                throw new PreexistingEntityException("FinalProjek " + finalProjek + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FinalProjek finalProjek) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            finalProjek = em.merge(finalProjek);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = finalProjek.getId();
                if (findFinalProjek(id) == null) {
                    throw new NonexistentEntityException("The finalProjek with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FinalProjek finalProjek;
            try {
                finalProjek = em.getReference(FinalProjek.class, id);
                finalProjek.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The finalProjek with id " + id + " no longer exists.", enfe);
            }
            em.remove(finalProjek);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FinalProjek> findFinalProjekEntities() {
        return findFinalProjekEntities(true, -1, -1);
    }

    public List<FinalProjek> findFinalProjekEntities(int maxResults, int firstResult) {
        return findFinalProjekEntities(false, maxResults, firstResult);
    }

    private List<FinalProjek> findFinalProjekEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FinalProjek.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FinalProjek findFinalProjek(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FinalProjek.class, id);
        } finally {
            em.close();
        }
    }

    public int getFinalProjekCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FinalProjek> rt = cq.from(FinalProjek.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
