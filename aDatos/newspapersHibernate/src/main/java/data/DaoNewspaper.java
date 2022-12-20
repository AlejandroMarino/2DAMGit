package data;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import modelo.Newspaper;

import java.util.List;

public class DaoNewspaper {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoNewspaper(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> list = null;
        em = jpaUtil.getEntityManager();

        try {
            list = em.createNamedQuery("NewspapersGetAll", Newspaper.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(list);
    }

    public Either<Integer, Newspaper> get(int id) {
        Newspaper n;
        em = jpaUtil.getEntityManager();

        try {
            n = em.find(Newspaper.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(n);
    }

    public int add(Newspaper n) {
        em = jpaUtil.getEntityManager();
        int r;
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(n);
            tx.commit();
            r = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            r = -1;
        } finally {
            if (em != null) em.close();
        }
        return r;
    }

    public int delete(Newspaper n) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        int r;
        tx.begin();
        try {
            em.remove(em.merge(n));
            tx.commit();
            r = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            r = -1;
        } finally {
            if (em != null) em.close();
        }
        return r;
    }

    public int update(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        int r;
        tx.begin();
        try {
            em.merge(newspaper);
            tx.commit();
            r = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            r = -1;
        } finally {
            if (em != null) em.close();
        }
        return r;
    }


}
