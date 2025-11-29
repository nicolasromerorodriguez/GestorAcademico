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
import Modelo.Acudiente;
import Modelo.Preinscripcion;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class PreinscripcionJpaController implements Serializable {

    public PreinscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PreinscripcionJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preinscripcion preinscripcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acudiente acudiente = preinscripcion.getAcudiente();
            if (acudiente != null) {
                acudiente = em.getReference(acudiente.getClass(), acudiente.getId());
                preinscripcion.setAcudiente(acudiente);
            }
            em.persist(preinscripcion);
            if (acudiente != null) {
                acudiente.getListaPrenscripciones().add(preinscripcion);
                acudiente = em.merge(acudiente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preinscripcion preinscripcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preinscripcion persistentPreinscripcion = em.find(Preinscripcion.class, preinscripcion.getId());
            Acudiente acudienteOld = persistentPreinscripcion.getAcudiente();
            Acudiente acudienteNew = preinscripcion.getAcudiente();
            if (acudienteNew != null) {
                acudienteNew = em.getReference(acudienteNew.getClass(), acudienteNew.getId());
                preinscripcion.setAcudiente(acudienteNew);
            }
            preinscripcion = em.merge(preinscripcion);
            if (acudienteOld != null && !acudienteOld.equals(acudienteNew)) {
                acudienteOld.getListaPrenscripciones().remove(preinscripcion);
                acudienteOld = em.merge(acudienteOld);
            }
            if (acudienteNew != null && !acudienteNew.equals(acudienteOld)) {
                acudienteNew.getListaPrenscripciones().add(preinscripcion);
                acudienteNew = em.merge(acudienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = preinscripcion.getId();
                if (findPreinscripcion(id) == null) {
                    throw new NonexistentEntityException("The preinscripcion with id " + id + " no longer exists.");
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
            Preinscripcion preinscripcion;
            try {
                preinscripcion = em.getReference(Preinscripcion.class, id);
                preinscripcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preinscripcion with id " + id + " no longer exists.", enfe);
            }
            Acudiente acudiente = preinscripcion.getAcudiente();
            if (acudiente != null) {
                acudiente.getListaPrenscripciones().remove(preinscripcion);
                acudiente = em.merge(acudiente);
            }
            em.remove(preinscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preinscripcion> findPreinscripcionEntities() {
        return findPreinscripcionEntities(true, -1, -1);
    }

    public List<Preinscripcion> findPreinscripcionEntities(int maxResults, int firstResult) {
        return findPreinscripcionEntities(false, maxResults, firstResult);
    }

    private List<Preinscripcion> findPreinscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preinscripcion.class));
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

    public Preinscripcion findPreinscripcion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preinscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreinscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preinscripcion> rt = cq.from(Preinscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
