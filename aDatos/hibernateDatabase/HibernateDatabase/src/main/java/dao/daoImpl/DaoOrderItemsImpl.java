package dao.daoImpl;

import common.Constants;
import config.JPAUtil;
import dao.DaoOrderItems;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoOrderItemsImpl implements DaoOrderItems {

    private JPAUtil jpaUtil;

    private EntityManager em;

    @Inject
    public DaoOrderItemsImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll(Order order) {
        List<OrderItem> l;
        em = jpaUtil.getEntityManager();
        try{
            l = em.createNamedQuery(Constants.HQL_GET_ALL_ORDER_ITEMS_OF_ORDER, OrderItem.class).setParameter("id", order.getId()).getResultList();
            for (OrderItem oi : l) {
                oi.getMenuItem().toString();
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
