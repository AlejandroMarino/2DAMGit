package dao.daoImpl;

import common.Constants;
import config.JPAUtil;
import dao.DaoQueries;
import domain.model.Customer;
import domain.model.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class DaoQueriesImpl implements DaoQueries {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoQueriesImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    @Override
    public Either<Integer, List<String>> getOrdersWithNumberOfItems() {
        List<String> l = new java.util.ArrayList<>(emptyList());
        em = jpaUtil.getEntityManager();
        try {
            List<Object[]> result = em.createNamedQuery(Constants.HQL_GET_ALL_ORDERS_WITH_NUMBER_OF_ITEMS, Object[].class).getResultList();
            for (Object[] o : result) {
                Order order = (Order) o[0];
                long numberOfItems = (long) o[1];
                l.add(order.toStringWithNumberOfItems(numberOfItems));
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
    public Either<Integer, String> getCustomerSpentMost() {
        String s;
        em = jpaUtil.getEntityManager();
        try {
            Object[] o = em.createNamedQuery(Constants.HQL_GET_CUSTOMER_SPENT_MORE_MONEY, Object[].class).getSingleResult();
            Customer customer = (Customer) o[0];
            double total = (double) o[1];
            s = customer.toStringWithMoneySpent(total);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(s);
    }
}
