package data;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.Reader;

import java.util.List;


public class DaoReader {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject

    public DaoReader(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<Reader>> getAll() {
        List<Reader> list;
        em = jpaUtil.getEntityManager();

        try {
            list = em.createNamedQuery("GetAllReaders", Reader.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(list);
    }

    public Either<Integer, Reader> get(int id) {
        Reader r;
        em = jpaUtil.getEntityManager();

        try {
            r = em.find(Reader.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(r);
    }

    public int add(Reader r) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(r);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            result = 0;
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public int delete(Reader r) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.remove(em.merge(r));
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            result = 0;
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public int update(Reader r) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.merge(r);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            result = 0;
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}
