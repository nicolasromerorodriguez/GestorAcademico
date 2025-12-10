/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Token;
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
public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RolJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaTokens() == null) {
            rol.setListaTokens(new ArrayList<Token>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Token> attachedListaTokens = new ArrayList<Token>();
            for (Token listaTokensTokenToAttach : rol.getListaTokens()) {
                listaTokensTokenToAttach = em.getReference(listaTokensTokenToAttach.getClass(), listaTokensTokenToAttach.getId());
                attachedListaTokens.add(listaTokensTokenToAttach);
            }
            rol.setListaTokens(attachedListaTokens);
            em.persist(rol);
            for (Token listaTokensToken : rol.getListaTokens()) {
                Rol oldRolOfListaTokensToken = listaTokensToken.getRol();
                listaTokensToken.setRol(rol);
                listaTokensToken = em.merge(listaTokensToken);
                if (oldRolOfListaTokensToken != null) {
                    oldRolOfListaTokensToken.getListaTokens().remove(listaTokensToken);
                    oldRolOfListaTokensToken = em.merge(oldRolOfListaTokensToken);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId());
            ArrayList<Token> listaTokensOld = persistentRol.getListaTokens();
            ArrayList<Token> listaTokensNew = rol.getListaTokens();
            ArrayList<Token> attachedListaTokensNew = new ArrayList<Token>();
            for (Token listaTokensNewTokenToAttach : listaTokensNew) {
                listaTokensNewTokenToAttach = em.getReference(listaTokensNewTokenToAttach.getClass(), listaTokensNewTokenToAttach.getId());
                attachedListaTokensNew.add(listaTokensNewTokenToAttach);
            }
            listaTokensNew = attachedListaTokensNew;
            rol.setListaTokens(listaTokensNew);
            rol = em.merge(rol);
            for (Token listaTokensOldToken : listaTokensOld) {
                if (!listaTokensNew.contains(listaTokensOldToken)) {
                    listaTokensOldToken.setRol(null);
                    listaTokensOldToken = em.merge(listaTokensOldToken);
                }
            }
            for (Token listaTokensNewToken : listaTokensNew) {
                if (!listaTokensOld.contains(listaTokensNewToken)) {
                    Rol oldRolOfListaTokensNewToken = listaTokensNewToken.getRol();
                    listaTokensNewToken.setRol(rol);
                    listaTokensNewToken = em.merge(listaTokensNewToken);
                    if (oldRolOfListaTokensNewToken != null && !oldRolOfListaTokensNewToken.equals(rol)) {
                        oldRolOfListaTokensNewToken.getListaTokens().remove(listaTokensNewToken);
                        oldRolOfListaTokensNewToken = em.merge(oldRolOfListaTokensNewToken);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Token> listaTokens = rol.getListaTokens();
            for (Token listaTokensToken : listaTokens) {
                listaTokensToken.setRol(null);
                listaTokensToken = em.merge(listaTokensToken);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
