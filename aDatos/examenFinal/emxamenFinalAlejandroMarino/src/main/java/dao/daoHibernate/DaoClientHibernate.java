package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.ClientHibernate;
import domain.model.modelHibernate.PurchaseHibernate;
import domain.model.modelHibernate.PurchasesItemsHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;

import java.util.List;

public class DaoClientHibernate {

    private final JPAUtil jpaUtil;

    private EntityManager em;

    @Inject
    public DaoClientHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, ClientHibernate> get(String name) {
        ClientHibernate c;
        em = jpaUtil.getEntityManager();
        try {
            c = em.createNamedQuery(Constants.HQL_GET_CLIENT_BY_NAME, ClientHibernate.class).setParameter("name", name).getSingleResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Either.left(Constants.ERROR_DELETING);
        } finally {
            em.close();
        }
        return Either.right(c);
    }

    public Either<Integer, Void> delete(ClientHibernate client, boolean withPurchases) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (withPurchases) {
                ClientHibernate c = em.find(ClientHibernate.class, client.getId());
                if (c != null) {
                    List<PurchaseHibernate> purchases = c.getPurchases();
                    for (PurchaseHibernate p : purchases) {
                        List<PurchasesItemsHibernate> purchasesItems = p.getItems();
                        for (PurchasesItemsHibernate pi : purchasesItems) {
                            em.remove(em.merge(pi));
                        }
                        em.remove(em.merge(p));
                    }
                }
            }
            em.remove(em.merge(client));
            em.getTransaction().commit();
        } catch (RollbackException e) {
            return Either.left(Constants.HAS_PURCHASES);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(Constants.ERROR_DELETING);
        } finally {
            em.close();
        }
        return Either.right(null);
    }
}
