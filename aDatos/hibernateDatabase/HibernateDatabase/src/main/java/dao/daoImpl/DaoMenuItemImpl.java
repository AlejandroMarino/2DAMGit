package dao.daoImpl;

import common.Constants;
import config.JPAUtil;
import dao.DaoMenuItem;
import domain.model.Customer;
import domain.model.MenuItem;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;

import java.util.List;

public class DaoMenuItemImpl implements DaoMenuItem {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoMenuItemImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    @Override
    public Either<Integer, List<String>> getAll(Customer customer) {
        List<String> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_NAME_OF_ITEMS_ORDERED_BY_CUSTOMER, String.class).setParameter("id", customer.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

    @Override
    public Either<Integer, MenuItem> get(String name) {
        MenuItem mi;
        em = jpaUtil.getEntityManager();
        try {
            mi = em.createNamedQuery(Constants.HQL_GET_MENU_ITEM_BY_NAME, MenuItem.class).setParameter("name", name.trim().toLowerCase()).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(mi);
    }

    @Override
    public Either<Integer, Void> save(MenuItem menuItem) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(menuItem);
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
    public Either<Integer, Void> delete(MenuItem menuItem, boolean withOrders) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (withOrders) {
                List<OrderItem> orderItems = em.createQuery("select oi from OrderItem oi where oi.menuItem.id = :id", OrderItem.class)
                        .setParameter("id", menuItem.getId()).getResultList();

                for (OrderItem oi : orderItems) {
                    em.remove(oi);
                }
            }
            em.remove(em.merge(menuItem));
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
