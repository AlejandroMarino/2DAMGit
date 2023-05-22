package dao.daoHibernate.daoHibernateImpl;

import common.Constants;
import config.JPAUtil;
import dao.daoHibernate.DaoCustomer;
import domain.model.modelHibernate.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoCustomerImpl implements DaoCustomer {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomerImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<Customer>> getAll(boolean withOrders) {
        List<Customer> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_ALL_CUSTOMERS, Customer.class).getResultList();
            if (withOrders) {
                for (Customer c : l) {
                    c.getOrders().size();
                    c.getOrders().forEach(order -> order.getTable().toString());
                    c.getOrders().forEach(order -> order.getOrderItems().size());
                    c.getOrders().forEach(order -> order.getOrderItems().forEach(orderItem -> orderItem.getMenuItem().toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

}