package data;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.Article;
import modelo.Reader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoArticle {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoArticle(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<Article>> getAll() {
        List<Article> list;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("GetAllArticles", Article.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }finally {
            if (em != null) em.close();
        }
        return Either.right(list);
    }

    public Either<Integer, Article> get(int id) {
        Article r;
        em = jpaUtil.getEntityManager();
        try {
            r = em.find(Article.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }
        return Either.right(r);
    }

    public int add(Article article) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.persist(article);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            result = 0;
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public int delete(Article article) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.remove(em.merge(article));
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            result = 0;
        }finally {
            if (em != null) em.close();
        }
        return result;
    }

    public int update(Article article) {
        em = jpaUtil.getEntityManager();
        int result;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.merge(article);
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            result = 0;
        }finally {
            if (em != null) em.close();
        }
        return result;
    }

}

