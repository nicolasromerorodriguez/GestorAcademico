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
import Modelo.Curso;
import Modelo.Grado;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class GradoJpaController implements Serializable {

    public GradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public GradoJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grado grado) {
        if (grado.getListaCursos() == null) {
            grado.setListaCursos(new ArrayList<Curso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Curso> attachedListaCursos = new ArrayList<Curso>();
            for (Curso listaCursosCursoToAttach : grado.getListaCursos()) {
                listaCursosCursoToAttach = em.getReference(listaCursosCursoToAttach.getClass(), listaCursosCursoToAttach.getId());
                attachedListaCursos.add(listaCursosCursoToAttach);
            }
            grado.setListaCursos(attachedListaCursos);
            em.persist(grado);
            for (Curso listaCursosCurso : grado.getListaCursos()) {
                Grado oldGradoOfListaCursosCurso = listaCursosCurso.getGrado();
                listaCursosCurso.setGrado(grado);
                listaCursosCurso = em.merge(listaCursosCurso);
                if (oldGradoOfListaCursosCurso != null) {
                    oldGradoOfListaCursosCurso.getListaCursos().remove(listaCursosCurso);
                    oldGradoOfListaCursosCurso = em.merge(oldGradoOfListaCursosCurso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grado grado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grado persistentGrado = em.find(Grado.class, grado.getId());
            ArrayList<Curso> listaCursosOld = persistentGrado.getListaCursos();
            ArrayList<Curso> listaCursosNew = grado.getListaCursos();
            ArrayList<Curso> attachedListaCursosNew = new ArrayList<Curso>();
            for (Curso listaCursosNewCursoToAttach : listaCursosNew) {
                listaCursosNewCursoToAttach = em.getReference(listaCursosNewCursoToAttach.getClass(), listaCursosNewCursoToAttach.getId());
                attachedListaCursosNew.add(listaCursosNewCursoToAttach);
            }
            listaCursosNew = attachedListaCursosNew;
            grado.setListaCursos(listaCursosNew);
            grado = em.merge(grado);
            for (Curso listaCursosOldCurso : listaCursosOld) {
                if (!listaCursosNew.contains(listaCursosOldCurso)) {
                    listaCursosOldCurso.setGrado(null);
                    listaCursosOldCurso = em.merge(listaCursosOldCurso);
                }
            }
            for (Curso listaCursosNewCurso : listaCursosNew) {
                if (!listaCursosOld.contains(listaCursosNewCurso)) {
                    Grado oldGradoOfListaCursosNewCurso = listaCursosNewCurso.getGrado();
                    listaCursosNewCurso.setGrado(grado);
                    listaCursosNewCurso = em.merge(listaCursosNewCurso);
                    if (oldGradoOfListaCursosNewCurso != null && !oldGradoOfListaCursosNewCurso.equals(grado)) {
                        oldGradoOfListaCursosNewCurso.getListaCursos().remove(listaCursosNewCurso);
                        oldGradoOfListaCursosNewCurso = em.merge(oldGradoOfListaCursosNewCurso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = grado.getId();
                if (findGrado(id) == null) {
                    throw new NonexistentEntityException("The grado with id " + id + " no longer exists.");
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
            Grado grado;
            try {
                grado = em.getReference(Grado.class, id);
                grado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grado with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Curso> listaCursos = grado.getListaCursos();
            for (Curso listaCursosCurso : listaCursos) {
                listaCursosCurso.setGrado(null);
                listaCursosCurso = em.merge(listaCursosCurso);
            }
            em.remove(grado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grado> findGradoEntities() {
        return findGradoEntities(true, -1, -1);
    }

    public List<Grado> findGradoEntities(int maxResults, int firstResult) {
        return findGradoEntities(false, maxResults, firstResult);
    }

    private List<Grado> findGradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grado.class));
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

    public Grado findGrado(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grado.class, id);
        } finally {
            em.close();
        }
    }

    public int getGradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grado> rt = cq.from(Grado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
