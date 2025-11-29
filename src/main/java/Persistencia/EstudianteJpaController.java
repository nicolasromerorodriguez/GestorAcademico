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
import Modelo.Estudiante;
import Modelo.HistoriaAcademica;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EstudianteJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acudiente acudienteEstudiante = estudiante.getAcudienteEstudiante();
            if (acudienteEstudiante != null) {
                acudienteEstudiante = em.getReference(acudienteEstudiante.getClass(), acudienteEstudiante.getId());
                estudiante.setAcudienteEstudiante(acudienteEstudiante);
            }
            HistoriaAcademica historiaAcademica = estudiante.getHistoriaAcademica();
            if (historiaAcademica != null) {
                historiaAcademica = em.getReference(historiaAcademica.getClass(), historiaAcademica.getId());
                estudiante.setHistoriaAcademica(historiaAcademica);
            }
            em.persist(estudiante);
            if (acudienteEstudiante != null) {
                acudienteEstudiante.getListaEstudiantes().add(estudiante);
                acudienteEstudiante = em.merge(acudienteEstudiante);
            }
            if (historiaAcademica != null) {
                historiaAcademica.getEstudiante().add(estudiante);
                historiaAcademica = em.merge(historiaAcademica);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getId());
            Acudiente acudienteEstudianteOld = persistentEstudiante.getAcudienteEstudiante();
            Acudiente acudienteEstudianteNew = estudiante.getAcudienteEstudiante();
            HistoriaAcademica historiaAcademicaOld = persistentEstudiante.getHistoriaAcademica();
            HistoriaAcademica historiaAcademicaNew = estudiante.getHistoriaAcademica();
            if (acudienteEstudianteNew != null) {
                acudienteEstudianteNew = em.getReference(acudienteEstudianteNew.getClass(), acudienteEstudianteNew.getId());
                estudiante.setAcudienteEstudiante(acudienteEstudianteNew);
            }
            if (historiaAcademicaNew != null) {
                historiaAcademicaNew = em.getReference(historiaAcademicaNew.getClass(), historiaAcademicaNew.getId());
                estudiante.setHistoriaAcademica(historiaAcademicaNew);
            }
            estudiante = em.merge(estudiante);
            if (acudienteEstudianteOld != null && !acudienteEstudianteOld.equals(acudienteEstudianteNew)) {
                acudienteEstudianteOld.getListaEstudiantes().remove(estudiante);
                acudienteEstudianteOld = em.merge(acudienteEstudianteOld);
            }
            if (acudienteEstudianteNew != null && !acudienteEstudianteNew.equals(acudienteEstudianteOld)) {
                acudienteEstudianteNew.getListaEstudiantes().add(estudiante);
                acudienteEstudianteNew = em.merge(acudienteEstudianteNew);
            }
            if (historiaAcademicaOld != null && !historiaAcademicaOld.equals(historiaAcademicaNew)) {
                historiaAcademicaOld.getEstudiante().remove(estudiante);
                historiaAcademicaOld = em.merge(historiaAcademicaOld);
            }
            if (historiaAcademicaNew != null && !historiaAcademicaNew.equals(historiaAcademicaOld)) {
                historiaAcademicaNew.getEstudiante().add(estudiante);
                historiaAcademicaNew = em.merge(historiaAcademicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = estudiante.getId();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            Acudiente acudienteEstudiante = estudiante.getAcudienteEstudiante();
            if (acudienteEstudiante != null) {
                acudienteEstudiante.getListaEstudiantes().remove(estudiante);
                acudienteEstudiante = em.merge(acudienteEstudiante);
            }
            HistoriaAcademica historiaAcademica = estudiante.getHistoriaAcademica();
            if (historiaAcademica != null) {
                historiaAcademica.getEstudiante().remove(estudiante);
                historiaAcademica = em.merge(historiaAcademica);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
