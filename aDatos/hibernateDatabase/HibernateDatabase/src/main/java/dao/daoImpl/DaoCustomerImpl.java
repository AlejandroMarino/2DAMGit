package dao.daoImpl;

import common.Constants;
import config.JPAUtil;
import dao.DaoCustomer;
import domain.model.Customer;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;

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

    @Override
    public Either<Integer, Customer> get(int id) {
        Customer c;
        em = jpaUtil.getEntityManager();
        try {
            c = em.createNamedQuery(Constants.HQL_GET_CUSTOMER, Customer.class).setParameter("id", id).getSingleResult();
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
    public Either<Integer, Void> delete(Customer customer, boolean withOrders) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (withOrders) {
                Customer c = em.find(Customer.class, customer.getId());
                if (c != null) {
                    List<Order> orders = c.getOrders();
                    for (Order o : orders) {
                        List<OrderItem> orderItems = o.getOrderItems();
                        for (OrderItem oi : orderItems) {
                            em.remove(em.merge(oi));
                        }
                        em.remove(em.merge(o));
                    }
                }
            }
            em.remove(em.merge(customer));
            em.getTransaction().commit();
        } catch (RollbackException e) {
            return Either.left(-2);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(null);
    }


}
