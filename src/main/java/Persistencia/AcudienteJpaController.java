/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Acudiente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Preinscripcion;
import java.util.ArrayList;
import Modelo.Estudiante;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicol
 */
public class AcudienteJpaController implements Serializable {

    public AcudienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public AcudienteJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Acudiente acudiente) throws PreexistingEntityException, Exception {
        if (acudiente.getListaPrenscripciones() == null) {
            acudiente.setListaPrenscripciones(new ArrayList<Preinscripcion>());
        }
        if (acudiente.getListaEstudiantes() == null) {
            acudiente.setListaEstudiantes(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Preinscripcion> attachedListaPrenscripciones = new ArrayList<Preinscripcion>();
            for (Preinscripcion listaPrenscripcionesPreinscripcionToAttach : acudiente.getListaPrenscripciones()) {
                listaPrenscripcionesPreinscripcionToAttach = em.getReference(listaPrenscripcionesPreinscripcionToAttach.getClass(), listaPrenscripcionesPreinscripcionToAttach.getId());
                attachedListaPrenscripciones.add(listaPrenscripcionesPreinscripcionToAttach);
            }
            acudiente.setListaPrenscripciones(attachedListaPrenscripciones);
            ArrayList<Estudiante> attachedListaEstudiantes = new ArrayList<Estudiante>();
            for (Estudiante listaEstudiantesEstudianteToAttach : acudiente.getListaEstudiantes()) {
                listaEstudiantesEstudianteToAttach = em.getReference(listaEstudiantesEstudianteToAttach.getClass(), listaEstudiantesEstudianteToAttach.getId());
                attachedListaEstudiantes.add(listaEstudiantesEstudianteToAttach);
            }
            acudiente.setListaEstudiantes(attachedListaEstudiantes);
            em.persist(acudiente);
            for (Preinscripcion listaPrenscripcionesPreinscripcion : acudiente.getListaPrenscripciones()) {
                Acudiente oldAcudienteOfListaPrenscripcionesPreinscripcion = listaPrenscripcionesPreinscripcion.getAcudiente();
                listaPrenscripcionesPreinscripcion.setAcudiente(acudiente);
                listaPrenscripcionesPreinscripcion = em.merge(listaPrenscripcionesPreinscripcion);
                if (oldAcudienteOfListaPrenscripcionesPreinscripcion != null) {
                    oldAcudienteOfListaPrenscripcionesPreinscripcion.getListaPrenscripciones().remove(listaPrenscripcionesPreinscripcion);
                    oldAcudienteOfListaPrenscripcionesPreinscripcion = em.merge(oldAcudienteOfListaPrenscripcionesPreinscripcion);
                }
            }
            for (Estudiante listaEstudiantesEstudiante : acudiente.getListaEstudiantes()) {
                Acudiente oldAcudienteEstudianteOfListaEstudiantesEstudiante = listaEstudiantesEstudiante.getAcudienteEstudiante();
                listaEstudiantesEstudiante.setAcudienteEstudiante(acudiente);
                listaEstudiantesEstudiante = em.merge(listaEstudiantesEstudiante);
                if (oldAcudienteEstudianteOfListaEstudiantesEstudiante != null) {
                    oldAcudienteEstudianteOfListaEstudiantesEstudiante.getListaEstudiantes().remove(listaEstudiantesEstudiante);
                    oldAcudienteEstudianteOfListaEstudiantesEstudiante = em.merge(oldAcudienteEstudianteOfListaEstudiantesEstudiante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAcudiente(acudiente.getId()) != null) {
                throw new PreexistingEntityException("Acudiente " + acudiente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Acudiente acudiente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acudiente persistentAcudiente = em.find(Acudiente.class, acudiente.getId());
            ArrayList<Preinscripcion> listaPrenscripcionesOld = persistentAcudiente.getListaPrenscripciones();
            ArrayList<Preinscripcion> listaPrenscripcionesNew = acudiente.getListaPrenscripciones();
            ArrayList<Estudiante> listaEstudiantesOld = persistentAcudiente.getListaEstudiantes();
            ArrayList<Estudiante> listaEstudiantesNew = acudiente.getListaEstudiantes();
            ArrayList<Preinscripcion> attachedListaPrenscripcionesNew = new ArrayList<Preinscripcion>();
            for (Preinscripcion listaPrenscripcionesNewPreinscripcionToAttach : listaPrenscripcionesNew) {
                listaPrenscripcionesNewPreinscripcionToAttach = em.getReference(listaPrenscripcionesNewPreinscripcionToAttach.getClass(), listaPrenscripcionesNewPreinscripcionToAttach.getId());
                attachedListaPrenscripcionesNew.add(listaPrenscripcionesNewPreinscripcionToAttach);
            }
            listaPrenscripcionesNew = attachedListaPrenscripcionesNew;
            acudiente.setListaPrenscripciones(listaPrenscripcionesNew);
            ArrayList<Estudiante> attachedListaEstudiantesNew = new ArrayList<Estudiante>();
            for (Estudiante listaEstudiantesNewEstudianteToAttach : listaEstudiantesNew) {
                listaEstudiantesNewEstudianteToAttach = em.getReference(listaEstudiantesNewEstudianteToAttach.getClass(), listaEstudiantesNewEstudianteToAttach.getId());
                attachedListaEstudiantesNew.add(listaEstudiantesNewEstudianteToAttach);
            }
            listaEstudiantesNew = attachedListaEstudiantesNew;
            acudiente.setListaEstudiantes(listaEstudiantesNew);
            acudiente = em.merge(acudiente);
            for (Preinscripcion listaPrenscripcionesOldPreinscripcion : listaPrenscripcionesOld) {
                if (!listaPrenscripcionesNew.contains(listaPrenscripcionesOldPreinscripcion)) {
                    listaPrenscripcionesOldPreinscripcion.setAcudiente(null);
                    listaPrenscripcionesOldPreinscripcion = em.merge(listaPrenscripcionesOldPreinscripcion);
                }
            }
            for (Preinscripcion listaPrenscripcionesNewPreinscripcion : listaPrenscripcionesNew) {
                if (!listaPrenscripcionesOld.contains(listaPrenscripcionesNewPreinscripcion)) {
                    Acudiente oldAcudienteOfListaPrenscripcionesNewPreinscripcion = listaPrenscripcionesNewPreinscripcion.getAcudiente();
                    listaPrenscripcionesNewPreinscripcion.setAcudiente(acudiente);
                    listaPrenscripcionesNewPreinscripcion = em.merge(listaPrenscripcionesNewPreinscripcion);
                    if (oldAcudienteOfListaPrenscripcionesNewPreinscripcion != null && !oldAcudienteOfListaPrenscripcionesNewPreinscripcion.equals(acudiente)) {
                        oldAcudienteOfListaPrenscripcionesNewPreinscripcion.getListaPrenscripciones().remove(listaPrenscripcionesNewPreinscripcion);
                        oldAcudienteOfListaPrenscripcionesNewPreinscripcion = em.merge(oldAcudienteOfListaPrenscripcionesNewPreinscripcion);
                    }
                }
            }
            for (Estudiante listaEstudiantesOldEstudiante : listaEstudiantesOld) {
                if (!listaEstudiantesNew.contains(listaEstudiantesOldEstudiante)) {
                    listaEstudiantesOldEstudiante.setAcudienteEstudiante(null);
                    listaEstudiantesOldEstudiante = em.merge(listaEstudiantesOldEstudiante);
                }
            }
            for (Estudiante listaEstudiantesNewEstudiante : listaEstudiantesNew) {
                if (!listaEstudiantesOld.contains(listaEstudiantesNewEstudiante)) {
                    Acudiente oldAcudienteEstudianteOfListaEstudiantesNewEstudiante = listaEstudiantesNewEstudiante.getAcudienteEstudiante();
                    listaEstudiantesNewEstudiante.setAcudienteEstudiante(acudiente);
                    listaEstudiantesNewEstudiante = em.merge(listaEstudiantesNewEstudiante);
                    if (oldAcudienteEstudianteOfListaEstudiantesNewEstudiante != null && !oldAcudienteEstudianteOfListaEstudiantesNewEstudiante.equals(acudiente)) {
                        oldAcudienteEstudianteOfListaEstudiantesNewEstudiante.getListaEstudiantes().remove(listaEstudiantesNewEstudiante);
                        oldAcudienteEstudianteOfListaEstudiantesNewEstudiante = em.merge(oldAcudienteEstudianteOfListaEstudiantesNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = acudiente.getId();
                if (findAcudiente(id) == null) {
                    throw new NonexistentEntityException("The acudiente with id " + id + " no longer exists.");
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
            Acudiente acudiente;
            try {
                acudiente = em.getReference(Acudiente.class, id);
                acudiente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acudiente with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Preinscripcion> listaPrenscripciones = acudiente.getListaPrenscripciones();
            for (Preinscripcion listaPrenscripcionesPreinscripcion : listaPrenscripciones) {
                listaPrenscripcionesPreinscripcion.setAcudiente(null);
                listaPrenscripcionesPreinscripcion = em.merge(listaPrenscripcionesPreinscripcion);
            }
            ArrayList<Estudiante> listaEstudiantes = acudiente.getListaEstudiantes();
            for (Estudiante listaEstudiantesEstudiante : listaEstudiantes) {
                listaEstudiantesEstudiante.setAcudienteEstudiante(null);
                listaEstudiantesEstudiante = em.merge(listaEstudiantesEstudiante);
            }
            em.remove(acudiente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Acudiente> findAcudienteEntities() {
        return findAcudienteEntities(true, -1, -1);
    }

    public List<Acudiente> findAcudienteEntities(int maxResults, int firstResult) {
        return findAcudienteEntities(false, maxResults, firstResult);
    }

    private List<Acudiente> findAcudienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Acudiente.class));
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

    public Acudiente findAcudiente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Acudiente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcudienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Acudiente> rt = cq.from(Acudiente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
