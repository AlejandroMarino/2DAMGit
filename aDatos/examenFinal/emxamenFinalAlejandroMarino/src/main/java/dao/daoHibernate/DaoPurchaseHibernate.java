package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.PurchaseHibernate;
import domain.model.modelHibernate.PurchasesItemsHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoPurchaseHibernate {

    private final JPAUtil jpaUtil;

    private EntityManager em;

    @Inject
    public DaoPurchaseHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, Void> save(PurchaseHibernate purchase) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            for (PurchasesItemsHibernate item : purchase.getItems()) {
                item.setPurchase(purchase);
            }
            em.persist(purchase);
            em.merge(purchase.getClient());
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(null);
    }

    public Either<Integer, List<PurchaseHibernate>> getAll() {
        List<PurchaseHibernate> purchases;
        em = jpaUtil.getEntityManager();
        try {
            purchases = em.createNamedQuery(Constants.HQL_GET_ALL_PURCHASES, PurchaseHibernate.class).getResultList();
            for (PurchaseHibernate p : purchases){
                p.getClient().toString();
                p.getItems().forEach(pi -> pi.getItem().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }finally {
            em.close();
        }
        return Either.right(purchases);
    }
}
