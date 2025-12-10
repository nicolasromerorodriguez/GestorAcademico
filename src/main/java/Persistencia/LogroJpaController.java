/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.CategoriaLogro;
import Modelo.Logro;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class LogroJpaController implements Serializable {

    public LogroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public LogroJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logro logro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaLogro categoriaLogro = logro.getCategoriaLogro();
            if (categoriaLogro != null) {
                categoriaLogro = em.getReference(categoriaLogro.getClass(), categoriaLogro.getId());
                logro.setCategoriaLogro(categoriaLogro);
            }
            em.persist(logro);
            if (categoriaLogro != null) {
                categoriaLogro.getListaLogros().add(logro);
                categoriaLogro = em.merge(categoriaLogro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logro logro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logro persistentLogro = em.find(Logro.class, logro.getId());
            CategoriaLogro categoriaLogroOld = persistentLogro.getCategoriaLogro();
            CategoriaLogro categoriaLogroNew = logro.getCategoriaLogro();
            if (categoriaLogroNew != null) {
                categoriaLogroNew = em.getReference(categoriaLogroNew.getClass(), categoriaLogroNew.getId());
                logro.setCategoriaLogro(categoriaLogroNew);
            }
            logro = em.merge(logro);
            if (categoriaLogroOld != null && !categoriaLogroOld.equals(categoriaLogroNew)) {
                categoriaLogroOld.getListaLogros().remove(logro);
                categoriaLogroOld = em.merge(categoriaLogroOld);
            }
            if (categoriaLogroNew != null && !categoriaLogroNew.equals(categoriaLogroOld)) {
                categoriaLogroNew.getListaLogros().add(logro);
                categoriaLogroNew = em.merge(categoriaLogroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = logro.getId();
                if (findLogro(id) == null) {
                    throw new NonexistentEntityException("The logro with id " + id + " no longer exists.");
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
            Logro logro;
            try {
                logro = em.getReference(Logro.class, id);
                logro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logro with id " + id + " no longer exists.", enfe);
            }
            CategoriaLogro categoriaLogro = logro.getCategoriaLogro();
            if (categoriaLogro != null) {
                categoriaLogro.getListaLogros().remove(logro);
                categoriaLogro = em.merge(categoriaLogro);
            }
            em.remove(logro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logro> findLogroEntities() {
        return findLogroEntities(true, -1, -1);
    }

    public List<Logro> findLogroEntities(int maxResults, int firstResult) {
        return findLogroEntities(false, maxResults, firstResult);
    }

    private List<Logro> findLogroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logro.class));
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

    public Logro findLogro(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logro> rt = cq.from(Logro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
