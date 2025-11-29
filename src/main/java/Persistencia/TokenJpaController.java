/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Token;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author nicol
 */
public class TokenJpaController implements Serializable {

    public TokenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public TokenJpaController(){
        emf = Persistence.createEntityManagerFactory("Colegio1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Token token) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findToken(token.getId()) != null) {
                throw new PreexistingEntityException("Token " + token + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Token token) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            token = em.merge(token);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = token.getId();
                if (findToken(id) == null) {
                    throw new NonexistentEntityException("The token with id " + id + " no longer exists.");
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
            Token token;
            try {
                token = em.getReference(Token.class, id);
                token.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The token with id " + id + " no longer exists.", enfe);
            }
            em.remove(token);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Token> findTokenEntities() {
        return findTokenEntities(true, -1, -1);
    }

    public List<Token> findTokenEntities(int maxResults, int firstResult) {
        return findTokenEntities(false, maxResults, firstResult);
    }

    private List<Token> findTokenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Token.class));
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

    public Token findToken(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Token.class, id);
        } finally {
            em.close();
        }
    }

    public int getTokenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Token> rt = cq.from(Token.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
