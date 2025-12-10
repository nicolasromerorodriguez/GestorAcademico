/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Boletin;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.HistoriaAcademica;
import Modelo.CategoriaLogro;
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
public class BoletinJpaController implements Serializable {

    public BoletinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public BoletinJpaController() {
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boletin boletin) {
        if (boletin.getCategoriaLogro() == null) {
            boletin.setCategoriaLogro(new ArrayList<CategoriaLogro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaAcademica historiaAcademica = boletin.getHistoriaAcademica();
            if (historiaAcademica != null) {
                historiaAcademica = em.getReference(historiaAcademica.getClass(), historiaAcademica.getId());
                boletin.setHistoriaAcademica(historiaAcademica);
            }
            ArrayList<CategoriaLogro> attachedCategoriaLogro = new ArrayList<CategoriaLogro>();
            for (CategoriaLogro categoriaLogroCategoriaLogroToAttach : boletin.getCategoriaLogro()) {
                categoriaLogroCategoriaLogroToAttach = em.getReference(categoriaLogroCategoriaLogroToAttach.getClass(), categoriaLogroCategoriaLogroToAttach.getId());
                attachedCategoriaLogro.add(categoriaLogroCategoriaLogroToAttach);
            }
            boletin.setCategoriaLogro(attachedCategoriaLogro);
            em.persist(boletin);
            if (historiaAcademica != null) {
                historiaAcademica.getListaBoletines().add(boletin);
                historiaAcademica = em.merge(historiaAcademica);
            }
            for (CategoriaLogro categoriaLogroCategoriaLogro : boletin.getCategoriaLogro()) {
                categoriaLogroCategoriaLogro.getBoletines().add(boletin);
                categoriaLogroCategoriaLogro = em.merge(categoriaLogroCategoriaLogro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boletin boletin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boletin persistentBoletin = em.find(Boletin.class, boletin.getId());
            HistoriaAcademica historiaAcademicaOld = persistentBoletin.getHistoriaAcademica();
            HistoriaAcademica historiaAcademicaNew = boletin.getHistoriaAcademica();
            ArrayList<CategoriaLogro> categoriaLogroOld = persistentBoletin.getCategoriaLogro();
            ArrayList<CategoriaLogro> categoriaLogroNew = boletin.getCategoriaLogro();
            if (historiaAcademicaNew != null) {
                historiaAcademicaNew = em.getReference(historiaAcademicaNew.getClass(), historiaAcademicaNew.getId());
                boletin.setHistoriaAcademica(historiaAcademicaNew);
            }
            ArrayList<CategoriaLogro> attachedCategoriaLogroNew = new ArrayList<CategoriaLogro>();
            for (CategoriaLogro categoriaLogroNewCategoriaLogroToAttach : categoriaLogroNew) {
                categoriaLogroNewCategoriaLogroToAttach = em.getReference(categoriaLogroNewCategoriaLogroToAttach.getClass(), categoriaLogroNewCategoriaLogroToAttach.getId());
                attachedCategoriaLogroNew.add(categoriaLogroNewCategoriaLogroToAttach);
            }
            categoriaLogroNew = attachedCategoriaLogroNew;
            boletin.setCategoriaLogro(categoriaLogroNew);
            boletin = em.merge(boletin);
            if (historiaAcademicaOld != null && !historiaAcademicaOld.equals(historiaAcademicaNew)) {
                historiaAcademicaOld.getListaBoletines().remove(boletin);
                historiaAcademicaOld = em.merge(historiaAcademicaOld);
            }
            if (historiaAcademicaNew != null && !historiaAcademicaNew.equals(historiaAcademicaOld)) {
                historiaAcademicaNew.getListaBoletines().add(boletin);
                historiaAcademicaNew = em.merge(historiaAcademicaNew);
            }
            for (CategoriaLogro categoriaLogroOldCategoriaLogro : categoriaLogroOld) {
                if (!categoriaLogroNew.contains(categoriaLogroOldCategoriaLogro)) {
                    categoriaLogroOldCategoriaLogro.getBoletines().remove(boletin);
                    categoriaLogroOldCategoriaLogro = em.merge(categoriaLogroOldCategoriaLogro);
                }
            }
            for (CategoriaLogro categoriaLogroNewCategoriaLogro : categoriaLogroNew) {
                if (!categoriaLogroOld.contains(categoriaLogroNewCategoriaLogro)) {
                    categoriaLogroNewCategoriaLogro.getBoletines().add(boletin);
                    categoriaLogroNewCategoriaLogro = em.merge(categoriaLogroNewCategoriaLogro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = boletin.getId();
                if (findBoletin(id) == null) {
                    throw new NonexistentEntityException("The boletin with id " + id + " no longer exists.");
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
            Boletin boletin;
            try {
                boletin = em.getReference(Boletin.class, id);
                boletin.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boletin with id " + id + " no longer exists.", enfe);
            }
            HistoriaAcademica historiaAcademica = boletin.getHistoriaAcademica();
            if (historiaAcademica != null) {
                historiaAcademica.getListaBoletines().remove(boletin);
                historiaAcademica = em.merge(historiaAcademica);
            }
            ArrayList<CategoriaLogro> categoriaLogro = boletin.getCategoriaLogro();
            for (CategoriaLogro categoriaLogroCategoriaLogro : categoriaLogro) {
                categoriaLogroCategoriaLogro.getBoletines().remove(boletin);
                categoriaLogroCategoriaLogro = em.merge(categoriaLogroCategoriaLogro);
            }
            em.remove(boletin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boletin> findBoletinEntities() {
        return findBoletinEntities(true, -1, -1);
    }

    public List<Boletin> findBoletinEntities(int maxResults, int firstResult) {
        return findBoletinEntities(false, maxResults, firstResult);
    }

    private List<Boletin> findBoletinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boletin.class));
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

    public Boletin findBoletin(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boletin.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boletin> rt = cq.from(Boletin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
