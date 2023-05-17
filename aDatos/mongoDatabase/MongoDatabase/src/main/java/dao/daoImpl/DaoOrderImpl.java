package dao.daoImpl;

import common.Constants;
import config.JPAUtil;
import dao.DaoOrder;
import domain.model.modelHibernate.Customer;
import domain.model.modelHibernate.Order;
import domain.model.modelHibernate.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoOrderImpl implements DaoOrder {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoOrderImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, Void> save(Order order) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.setOrder(order);
            }
            em.persist(order);
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
    public Either<Integer, List<Order>> getAll(Customer customer) {
        List<Order> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_ORDERS_OF_CUSTOMER, Order.class).setParameter("id", customer.getId()).getResultList();
            for (Order o : l) {
                o.getTable().toString();
                o.getCustomer().toString();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }



}
