package dao.daoImpl;

import config.JPAUtil;
import dao.DaoCustomer;
import domain.model.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoCustomerImpl implements DaoCustomer {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomerImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<Customer>> getAll() {
        List<Customer> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery("HQL_GET_ALL_CUSTOMERS", Customer.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

    @Override
    public Either<Integer, Customer> get(int id) {
        Customer c;
        em = jpaUtil.getEntityManager();
        try {
            c = em.createNamedQuery("HQL_GET_CUSTOMER", Customer.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(c);
    }

    @Override
    public Either<Integer, Customer> save(Customer customer) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(customer);
    }

    @Override
    public Either<Integer, Void> update(Customer customer) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(null);
    }

    @Override
    public Either<Integer, Void> delete(int id) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Customer.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(null);
    }


}
