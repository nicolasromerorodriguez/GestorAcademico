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
import Modelo.Boletin;
import Modelo.HistoriaAcademica;
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
public class HistoriaAcademicaJpaController implements Serializable {

    public HistoriaAcademicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public HistoriaAcademicaJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaAcademica historiaAcademica) {
        if (historiaAcademica.getListaBoletines() == null) {
            historiaAcademica.setListaBoletines(new ArrayList<Boletin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Boletin> attachedListaBoletines = new ArrayList<Boletin>();
            for (Boletin listaBoletinesBoletinToAttach : historiaAcademica.getListaBoletines()) {
                listaBoletinesBoletinToAttach = em.getReference(listaBoletinesBoletinToAttach.getClass(), listaBoletinesBoletinToAttach.getId());
                attachedListaBoletines.add(listaBoletinesBoletinToAttach);
            }
            historiaAcademica.setListaBoletines(attachedListaBoletines);
            em.persist(historiaAcademica);
            for (Boletin listaBoletinesBoletin : historiaAcademica.getListaBoletines()) {
                HistoriaAcademica oldHistoriaAcademicaOfListaBoletinesBoletin = listaBoletinesBoletin.getHistoriaAcademica();
                listaBoletinesBoletin.setHistoriaAcademica(historiaAcademica);
                listaBoletinesBoletin = em.merge(listaBoletinesBoletin);
                if (oldHistoriaAcademicaOfListaBoletinesBoletin != null) {
                    oldHistoriaAcademicaOfListaBoletinesBoletin.getListaBoletines().remove(listaBoletinesBoletin);
                    oldHistoriaAcademicaOfListaBoletinesBoletin = em.merge(oldHistoriaAcademicaOfListaBoletinesBoletin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaAcademica historiaAcademica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaAcademica persistentHistoriaAcademica = em.find(HistoriaAcademica.class, historiaAcademica.getId());
            ArrayList<Boletin> listaBoletinesOld = persistentHistoriaAcademica.getListaBoletines();
            ArrayList<Boletin> listaBoletinesNew = historiaAcademica.getListaBoletines();
            ArrayList<Boletin> attachedListaBoletinesNew = new ArrayList<Boletin>();
            for (Boletin listaBoletinesNewBoletinToAttach : listaBoletinesNew) {
                listaBoletinesNewBoletinToAttach = em.getReference(listaBoletinesNewBoletinToAttach.getClass(), listaBoletinesNewBoletinToAttach.getId());
                attachedListaBoletinesNew.add(listaBoletinesNewBoletinToAttach);
            }
            listaBoletinesNew = attachedListaBoletinesNew;
            historiaAcademica.setListaBoletines(listaBoletinesNew);
            historiaAcademica = em.merge(historiaAcademica);
            for (Boletin listaBoletinesOldBoletin : listaBoletinesOld) {
                if (!listaBoletinesNew.contains(listaBoletinesOldBoletin)) {
                    listaBoletinesOldBoletin.setHistoriaAcademica(null);
                    listaBoletinesOldBoletin = em.merge(listaBoletinesOldBoletin);
                }
            }
            for (Boletin listaBoletinesNewBoletin : listaBoletinesNew) {
                if (!listaBoletinesOld.contains(listaBoletinesNewBoletin)) {
                    HistoriaAcademica oldHistoriaAcademicaOfListaBoletinesNewBoletin = listaBoletinesNewBoletin.getHistoriaAcademica();
                    listaBoletinesNewBoletin.setHistoriaAcademica(historiaAcademica);
                    listaBoletinesNewBoletin = em.merge(listaBoletinesNewBoletin);
                    if (oldHistoriaAcademicaOfListaBoletinesNewBoletin != null && !oldHistoriaAcademicaOfListaBoletinesNewBoletin.equals(historiaAcademica)) {
                        oldHistoriaAcademicaOfListaBoletinesNewBoletin.getListaBoletines().remove(listaBoletinesNewBoletin);
                        oldHistoriaAcademicaOfListaBoletinesNewBoletin = em.merge(oldHistoriaAcademicaOfListaBoletinesNewBoletin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = historiaAcademica.getId();
                if (findHistoriaAcademica(id) == null) {
                    throw new NonexistentEntityException("The historiaAcademica with id " + id + " no longer exists.");
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
            HistoriaAcademica historiaAcademica;
            try {
                historiaAcademica = em.getReference(HistoriaAcademica.class, id);
                historiaAcademica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaAcademica with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Boletin> listaBoletines = historiaAcademica.getListaBoletines();
            for (Boletin listaBoletinesBoletin : listaBoletines) {
                listaBoletinesBoletin.setHistoriaAcademica(null);
                listaBoletinesBoletin = em.merge(listaBoletinesBoletin);
            }
            em.remove(historiaAcademica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriaAcademica> findHistoriaAcademicaEntities() {
        return findHistoriaAcademicaEntities(true, -1, -1);
    }

    public List<HistoriaAcademica> findHistoriaAcademicaEntities(int maxResults, int firstResult) {
        return findHistoriaAcademicaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaAcademica> findHistoriaAcademicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaAcademica.class));
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

    public HistoriaAcademica findHistoriaAcademica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaAcademica.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaAcademicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaAcademica> rt = cq.from(HistoriaAcademica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
