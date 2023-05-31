package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.ObjetoHibernate;
import domain.model.modelHibernate.UbicacionHibernate;
import domain.model.modelMongo.Objeto;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoObjetos {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoObjetos(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, ObjetoHibernate> get(int id){
        ObjetoHibernate o;
        em = jpaUtil.getEntityManager();
        try {
            o = em.createNamedQuery(Constants.HQL_GET_OBJETO, ObjetoHibernate.class).setParameter("id", id).getSingleResult();
            o.getUbicacion().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(o);
    }

    public Either<Integer, List<ObjetoHibernate>> getAll(UbicacionHibernate u) {
        List<ObjetoHibernate> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_OBJETOS_UBICACION, ObjetoHibernate.class).setParameter("id", u.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }
}
