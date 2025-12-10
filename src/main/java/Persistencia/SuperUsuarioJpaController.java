/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.SuperUsuario;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
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
public class SuperUsuarioJpaController implements Serializable {

    public SuperUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public SuperUsuarioJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuperUsuario superUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(superUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuperUsuario(superUsuario.getId()) != null) {
                throw new PreexistingEntityException("SuperUsuario " + superUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuperUsuario superUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            superUsuario = em.merge(superUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = superUsuario.getId();
                if (findSuperUsuario(id) == null) {
                    throw new NonexistentEntityException("The superUsuario with id " + id + " no longer exists.");
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
            SuperUsuario superUsuario;
            try {
                superUsuario = em.getReference(SuperUsuario.class, id);
                superUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The superUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(superUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuperUsuario> findSuperUsuarioEntities() {
        return findSuperUsuarioEntities(true, -1, -1);
    }

    public List<SuperUsuario> findSuperUsuarioEntities(int maxResults, int firstResult) {
        return findSuperUsuarioEntities(false, maxResults, firstResult);
    }

    private List<SuperUsuario> findSuperUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuperUsuario.class));
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

    public SuperUsuario findSuperUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuperUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuperUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuperUsuario> rt = cq.from(SuperUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
