package dao.daoImpl;

import config.JPAUtil;
import dao.DaoCustomer;
import domain.model.Customer;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class DaoCustomerImpl implements DaoCustomer {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomerImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<Customer> getAll() {
        List<Customer> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery("HQL_GET_ALL_CUSTOMERS", Customer.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
        return l;
    }
}
