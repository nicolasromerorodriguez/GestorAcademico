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
import Modelo.Logro;
import java.util.ArrayList;
import Modelo.Curso;
import Modelo.Boletin;
import Modelo.CategoriaLogro;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class CategoriaLogroJpaController implements Serializable {

    public CategoriaLogroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CategoriaLogroJpaController() {
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaLogro categoriaLogro) {
        if (categoriaLogro.getListaLogros() == null) {
            categoriaLogro.setListaLogros(new ArrayList<Logro>());
        }
        if (categoriaLogro.getCursos() == null) {
            categoriaLogro.setCursos(new ArrayList<Curso>());
        }
        if (categoriaLogro.getBoletines() == null) {
            categoriaLogro.setBoletines(new ArrayList<Boletin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Logro> attachedListaLogros = new ArrayList<Logro>();
            for (Logro listaLogrosLogroToAttach : categoriaLogro.getListaLogros()) {
                listaLogrosLogroToAttach = em.getReference(listaLogrosLogroToAttach.getClass(), listaLogrosLogroToAttach.getId());
                attachedListaLogros.add(listaLogrosLogroToAttach);
            }
            categoriaLogro.setListaLogros(attachedListaLogros);
            ArrayList<Curso> attachedCursos = new ArrayList<Curso>();
            for (Curso cursosCursoToAttach : categoriaLogro.getCursos()) {
                cursosCursoToAttach = em.getReference(cursosCursoToAttach.getClass(), cursosCursoToAttach.getId());
                attachedCursos.add(cursosCursoToAttach);
            }
            categoriaLogro.setCursos(attachedCursos);
            ArrayList<Boletin> attachedBoletines = new ArrayList<Boletin>();
            for (Boletin boletinesBoletinToAttach : categoriaLogro.getBoletines()) {
                boletinesBoletinToAttach = em.getReference(boletinesBoletinToAttach.getClass(), boletinesBoletinToAttach.getId());
                attachedBoletines.add(boletinesBoletinToAttach);
            }
            categoriaLogro.setBoletines(attachedBoletines);
            em.persist(categoriaLogro);
            for (Logro listaLogrosLogro : categoriaLogro.getListaLogros()) {
                CategoriaLogro oldCategoriaLogroOfListaLogrosLogro = listaLogrosLogro.getCategoriaLogro();
                listaLogrosLogro.setCategoriaLogro(categoriaLogro);
                listaLogrosLogro = em.merge(listaLogrosLogro);
                if (oldCategoriaLogroOfListaLogrosLogro != null) {
                    oldCategoriaLogroOfListaLogrosLogro.getListaLogros().remove(listaLogrosLogro);
                    oldCategoriaLogroOfListaLogrosLogro = em.merge(oldCategoriaLogroOfListaLogrosLogro);
                }
            }
            for (Curso cursosCurso : categoriaLogro.getCursos()) {
                cursosCurso.getCategorias().add(categoriaLogro);
                cursosCurso = em.merge(cursosCurso);
            }
            for (Boletin boletinesBoletin : categoriaLogro.getBoletines()) {
                boletinesBoletin.getCategoriaLogro().add(categoriaLogro);
                boletinesBoletin = em.merge(boletinesBoletin);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaLogro categoriaLogro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaLogro persistentCategoriaLogro = em.find(CategoriaLogro.class, categoriaLogro.getId());
            ArrayList<Logro> listaLogrosOld = persistentCategoriaLogro.getListaLogros();
            ArrayList<Logro> listaLogrosNew = categoriaLogro.getListaLogros();
            ArrayList<Curso> cursosOld = persistentCategoriaLogro.getCursos();
            ArrayList<Curso> cursosNew = categoriaLogro.getCursos();
            ArrayList<Boletin> boletinesOld = persistentCategoriaLogro.getBoletines();
            ArrayList<Boletin> boletinesNew = categoriaLogro.getBoletines();
            ArrayList<Logro> attachedListaLogrosNew = new ArrayList<Logro>();
            for (Logro listaLogrosNewLogroToAttach : listaLogrosNew) {
                listaLogrosNewLogroToAttach = em.getReference(listaLogrosNewLogroToAttach.getClass(), listaLogrosNewLogroToAttach.getId());
                attachedListaLogrosNew.add(listaLogrosNewLogroToAttach);
            }
            listaLogrosNew = attachedListaLogrosNew;
            categoriaLogro.setListaLogros(listaLogrosNew);
            ArrayList<Curso> attachedCursosNew = new ArrayList<Curso>();
            for (Curso cursosNewCursoToAttach : cursosNew) {
                cursosNewCursoToAttach = em.getReference(cursosNewCursoToAttach.getClass(), cursosNewCursoToAttach.getId());
                attachedCursosNew.add(cursosNewCursoToAttach);
            }
            cursosNew = attachedCursosNew;
            categoriaLogro.setCursos(cursosNew);
            ArrayList<Boletin> attachedBoletinesNew = new ArrayList<Boletin>();
            for (Boletin boletinesNewBoletinToAttach : boletinesNew) {
                boletinesNewBoletinToAttach = em.getReference(boletinesNewBoletinToAttach.getClass(), boletinesNewBoletinToAttach.getId());
                attachedBoletinesNew.add(boletinesNewBoletinToAttach);
            }
            boletinesNew = attachedBoletinesNew;
            categoriaLogro.setBoletines(boletinesNew);
            categoriaLogro = em.merge(categoriaLogro);
            for (Logro listaLogrosOldLogro : listaLogrosOld) {
                if (!listaLogrosNew.contains(listaLogrosOldLogro)) {
                    listaLogrosOldLogro.setCategoriaLogro(null);
                    listaLogrosOldLogro = em.merge(listaLogrosOldLogro);
                }
            }
            for (Logro listaLogrosNewLogro : listaLogrosNew) {
                if (!listaLogrosOld.contains(listaLogrosNewLogro)) {
                    CategoriaLogro oldCategoriaLogroOfListaLogrosNewLogro = listaLogrosNewLogro.getCategoriaLogro();
                    listaLogrosNewLogro.setCategoriaLogro(categoriaLogro);
                    listaLogrosNewLogro = em.merge(listaLogrosNewLogro);
                    if (oldCategoriaLogroOfListaLogrosNewLogro != null && !oldCategoriaLogroOfListaLogrosNewLogro.equals(categoriaLogro)) {
                        oldCategoriaLogroOfListaLogrosNewLogro.getListaLogros().remove(listaLogrosNewLogro);
                        oldCategoriaLogroOfListaLogrosNewLogro = em.merge(oldCategoriaLogroOfListaLogrosNewLogro);
                    }
                }
            }
            for (Curso cursosOldCurso : cursosOld) {
                if (!cursosNew.contains(cursosOldCurso)) {
                    cursosOldCurso.getCategorias().remove(categoriaLogro);
                    cursosOldCurso = em.merge(cursosOldCurso);
                }
            }
            for (Curso cursosNewCurso : cursosNew) {
                if (!cursosOld.contains(cursosNewCurso)) {
                    cursosNewCurso.getCategorias().add(categoriaLogro);
                    cursosNewCurso = em.merge(cursosNewCurso);
                }
            }
            for (Boletin boletinesOldBoletin : boletinesOld) {
                if (!boletinesNew.contains(boletinesOldBoletin)) {
                    boletinesOldBoletin.getCategoriaLogro().remove(categoriaLogro);
                    boletinesOldBoletin = em.merge(boletinesOldBoletin);
                }
            }
            for (Boletin boletinesNewBoletin : boletinesNew) {
                if (!boletinesOld.contains(boletinesNewBoletin)) {
                    boletinesNewBoletin.getCategoriaLogro().add(categoriaLogro);
                    boletinesNewBoletin = em.merge(boletinesNewBoletin);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = categoriaLogro.getId();
                if (findCategoriaLogro(id) == null) {
                    throw new NonexistentEntityException("The categoriaLogro with id " + id + " no longer exists.");
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
            CategoriaLogro categoriaLogro;
            try {
                categoriaLogro = em.getReference(CategoriaLogro.class, id);
                categoriaLogro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaLogro with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Logro> listaLogros = categoriaLogro.getListaLogros();
            for (Logro listaLogrosLogro : listaLogros) {
                listaLogrosLogro.setCategoriaLogro(null);
                listaLogrosLogro = em.merge(listaLogrosLogro);
            }
            ArrayList<Curso> cursos = categoriaLogro.getCursos();
            for (Curso cursosCurso : cursos) {
                cursosCurso.getCategorias().remove(categoriaLogro);
                cursosCurso = em.merge(cursosCurso);
            }
            ArrayList<Boletin> boletines = categoriaLogro.getBoletines();
            for (Boletin boletinesBoletin : boletines) {
                boletinesBoletin.getCategoriaLogro().remove(categoriaLogro);
                boletinesBoletin = em.merge(boletinesBoletin);
            }
            em.remove(categoriaLogro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaLogro> findCategoriaLogroEntities() {
        return findCategoriaLogroEntities(true, -1, -1);
    }

    public List<CategoriaLogro> findCategoriaLogroEntities(int maxResults, int firstResult) {
        return findCategoriaLogroEntities(false, maxResults, firstResult);
    }

    private List<CategoriaLogro> findCategoriaLogroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaLogro.class));
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

    public CategoriaLogro findCategoriaLogro(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaLogro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaLogroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaLogro> rt = cq.from(CategoriaLogro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
