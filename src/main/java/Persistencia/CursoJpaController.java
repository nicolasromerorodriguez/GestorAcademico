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
import Modelo.Grado;
import Modelo.Estudiante;
import java.util.ArrayList;
import Modelo.CategoriaLogro;
import Modelo.Curso;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CursoJpaController() {
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getListaEstudiantes() == null) {
            curso.setListaEstudiantes(new ArrayList<Estudiante>());
        }
        if (curso.getCategorias() == null) {
            curso.setCategorias(new ArrayList<CategoriaLogro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grado grado = curso.getGrado();
            if (grado != null) {
                grado = em.getReference(grado.getClass(), grado.getId());
                curso.setGrado(grado);
            }
            ArrayList<Estudiante> attachedListaEstudiantes = new ArrayList<Estudiante>();
            for (Estudiante listaEstudiantesEstudianteToAttach : curso.getListaEstudiantes()) {
                listaEstudiantesEstudianteToAttach = em.getReference(listaEstudiantesEstudianteToAttach.getClass(), listaEstudiantesEstudianteToAttach.getId());
                attachedListaEstudiantes.add(listaEstudiantesEstudianteToAttach);
            }
            curso.setListaEstudiantes(attachedListaEstudiantes);
            ArrayList<CategoriaLogro> attachedCategorias = new ArrayList<CategoriaLogro>();
            for (CategoriaLogro categoriasCategoriaLogroToAttach : curso.getCategorias()) {
                categoriasCategoriaLogroToAttach = em.getReference(categoriasCategoriaLogroToAttach.getClass(), categoriasCategoriaLogroToAttach.getId());
                attachedCategorias.add(categoriasCategoriaLogroToAttach);
            }
            curso.setCategorias(attachedCategorias);
            em.persist(curso);
            if (grado != null) {
                grado.getListaCursos().add(curso);
                grado = em.merge(grado);
            }
            for (Estudiante listaEstudiantesEstudiante : curso.getListaEstudiantes()) {
                Curso oldCursoEstudianteOfListaEstudiantesEstudiante = listaEstudiantesEstudiante.getCursoEstudiante();
                listaEstudiantesEstudiante.setCursoEstudiante(curso);
                listaEstudiantesEstudiante = em.merge(listaEstudiantesEstudiante);
                if (oldCursoEstudianteOfListaEstudiantesEstudiante != null) {
                    oldCursoEstudianteOfListaEstudiantesEstudiante.getListaEstudiantes().remove(listaEstudiantesEstudiante);
                    oldCursoEstudianteOfListaEstudiantesEstudiante = em.merge(oldCursoEstudianteOfListaEstudiantesEstudiante);
                }
            }
            for (CategoriaLogro categoriasCategoriaLogro : curso.getCategorias()) {
                categoriasCategoriaLogro.getCursos().add(curso);
                categoriasCategoriaLogro = em.merge(categoriasCategoriaLogro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getId());
            Grado gradoOld = persistentCurso.getGrado();
            Grado gradoNew = curso.getGrado();
            ArrayList<Estudiante> listaEstudiantesOld = persistentCurso.getListaEstudiantes();
            ArrayList<Estudiante> listaEstudiantesNew = curso.getListaEstudiantes();
            ArrayList<CategoriaLogro> categoriasOld = persistentCurso.getCategorias();
            ArrayList<CategoriaLogro> categoriasNew = curso.getCategorias();
            if (gradoNew != null) {
                gradoNew = em.getReference(gradoNew.getClass(), gradoNew.getId());
                curso.setGrado(gradoNew);
            }
            ArrayList<Estudiante> attachedListaEstudiantesNew = new ArrayList<Estudiante>();
            for (Estudiante listaEstudiantesNewEstudianteToAttach : listaEstudiantesNew) {
                listaEstudiantesNewEstudianteToAttach = em.getReference(listaEstudiantesNewEstudianteToAttach.getClass(), listaEstudiantesNewEstudianteToAttach.getId());
                attachedListaEstudiantesNew.add(listaEstudiantesNewEstudianteToAttach);
            }
            listaEstudiantesNew = attachedListaEstudiantesNew;
            curso.setListaEstudiantes(listaEstudiantesNew);
            ArrayList<CategoriaLogro> attachedCategoriasNew = new ArrayList<CategoriaLogro>();
            for (CategoriaLogro categoriasNewCategoriaLogroToAttach : categoriasNew) {
                categoriasNewCategoriaLogroToAttach = em.getReference(categoriasNewCategoriaLogroToAttach.getClass(), categoriasNewCategoriaLogroToAttach.getId());
                attachedCategoriasNew.add(categoriasNewCategoriaLogroToAttach);
            }
            categoriasNew = attachedCategoriasNew;
            curso.setCategorias(categoriasNew);
            curso = em.merge(curso);
            if (gradoOld != null && !gradoOld.equals(gradoNew)) {
                gradoOld.getListaCursos().remove(curso);
                gradoOld = em.merge(gradoOld);
            }
            if (gradoNew != null && !gradoNew.equals(gradoOld)) {
                gradoNew.getListaCursos().add(curso);
                gradoNew = em.merge(gradoNew);
            }
            for (Estudiante listaEstudiantesOldEstudiante : listaEstudiantesOld) {
                if (!listaEstudiantesNew.contains(listaEstudiantesOldEstudiante)) {
                    listaEstudiantesOldEstudiante.setCursoEstudiante(null);
                    listaEstudiantesOldEstudiante = em.merge(listaEstudiantesOldEstudiante);
                }
            }
            for (Estudiante listaEstudiantesNewEstudiante : listaEstudiantesNew) {
                if (!listaEstudiantesOld.contains(listaEstudiantesNewEstudiante)) {
                    Curso oldCursoEstudianteOfListaEstudiantesNewEstudiante = listaEstudiantesNewEstudiante.getCursoEstudiante();
                    listaEstudiantesNewEstudiante.setCursoEstudiante(curso);
                    listaEstudiantesNewEstudiante = em.merge(listaEstudiantesNewEstudiante);
                    if (oldCursoEstudianteOfListaEstudiantesNewEstudiante != null && !oldCursoEstudianteOfListaEstudiantesNewEstudiante.equals(curso)) {
                        oldCursoEstudianteOfListaEstudiantesNewEstudiante.getListaEstudiantes().remove(listaEstudiantesNewEstudiante);
                        oldCursoEstudianteOfListaEstudiantesNewEstudiante = em.merge(oldCursoEstudianteOfListaEstudiantesNewEstudiante);
                    }
                }
            }
            for (CategoriaLogro categoriasOldCategoriaLogro : categoriasOld) {
                if (!categoriasNew.contains(categoriasOldCategoriaLogro)) {
                    categoriasOldCategoriaLogro.getCursos().remove(curso);
                    categoriasOldCategoriaLogro = em.merge(categoriasOldCategoriaLogro);
                }
            }
            for (CategoriaLogro categoriasNewCategoriaLogro : categoriasNew) {
                if (!categoriasOld.contains(categoriasNewCategoriaLogro)) {
                    categoriasNewCategoriaLogro.getCursos().add(curso);
                    categoriasNewCategoriaLogro = em.merge(categoriasNewCategoriaLogro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            Grado grado = curso.getGrado();
            if (grado != null) {
                grado.getListaCursos().remove(curso);
                grado = em.merge(grado);
            }
            ArrayList<Estudiante> listaEstudiantes = curso.getListaEstudiantes();
            for (Estudiante listaEstudiantesEstudiante : listaEstudiantes) {
                listaEstudiantesEstudiante.setCursoEstudiante(null);
                listaEstudiantesEstudiante = em.merge(listaEstudiantesEstudiante);
            }
            ArrayList<CategoriaLogro> categorias = curso.getCategorias();
            for (CategoriaLogro categoriasCategoriaLogro : categorias) {
                categoriasCategoriaLogro.getCursos().remove(curso);
                categoriasCategoriaLogro = em.merge(categoriasCategoriaLogro);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
