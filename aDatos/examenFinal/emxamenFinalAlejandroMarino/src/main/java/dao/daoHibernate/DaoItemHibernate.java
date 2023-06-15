package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.ClientHibernate;
import domain.model.modelHibernate.ItemHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoItemHibernate {

    private final JPAUtil jpaUtil;

    private EntityManager em;

    @Inject
    public DaoItemHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<ItemHibernate>> getAll(ClientHibernate client) {
        List<ItemHibernate> items;
        em = jpaUtil.getEntityManager();
        try {
            items = em.createNamedQuery(Constants.HQL_GET_ALL_ITEMS_OF_CLIENT, ItemHibernate.class).setParameter("name", client.getName()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(items);
    }

    public Either<Integer, ItemHibernate> get(String name) {
        ItemHibernate i;
        em = jpaUtil.getEntityManager();
        try {
            i = em.createNamedQuery(Constants.HQL_GET_ITEM_BY_NAME, ItemHibernate.class).setParameter("name", name).getSingleResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(i);
    }
}
