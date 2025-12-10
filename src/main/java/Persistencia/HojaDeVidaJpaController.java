/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.HojaDeVida;
import Persistencia.exceptions.NonexistentEntityException;
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
 * @author nicol
 */
public class HojaDeVidaJpaController implements Serializable {

    public HojaDeVidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public HojaDeVidaJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HojaDeVida hojaDeVida) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hojaDeVida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HojaDeVida hojaDeVida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hojaDeVida = em.merge(hojaDeVida);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = hojaDeVida.getId();
                if (findHojaDeVida(id) == null) {
                    throw new NonexistentEntityException("The hojaDeVida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaDeVida hojaDeVida;
            try {
                hojaDeVida = em.getReference(HojaDeVida.class, id);
                hojaDeVida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaDeVida with id " + id + " no longer exists.", enfe);
            }
            em.remove(hojaDeVida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HojaDeVida> findHojaDeVidaEntities() {
        return findHojaDeVidaEntities(true, -1, -1);
    }

    public List<HojaDeVida> findHojaDeVidaEntities(int maxResults, int firstResult) {
        return findHojaDeVidaEntities(false, maxResults, firstResult);
    }

    private List<HojaDeVida> findHojaDeVidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HojaDeVida.class));
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

    public HojaDeVida findHojaDeVida(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HojaDeVida.class, id);
        } finally {
            em.close();
        }
    }

    public int getHojaDeVidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HojaDeVida> rt = cq.from(HojaDeVida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
