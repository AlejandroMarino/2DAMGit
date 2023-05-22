package dao.daoHibernate.daoHibernateImpl;

import common.Constants;
import config.JPAUtil;
import dao.daoHibernate.DaoMenuItem;
import domain.model.modelHibernate.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoMenuItemImpl implements DaoMenuItem {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoMenuItemImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, List<MenuItem>> getAll() {
        List<MenuItem> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_ALL_MENU_ITEMS, MenuItem.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }
}
