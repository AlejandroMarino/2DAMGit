package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.PermisosHibernate;
import domain.model.modelHibernate.PermisosObjetosHibernate;
import domain.model.modelHibernate.PermisosUbicacionesHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoPermisos {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoPermisos(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<PermisosHibernate>> getAll(PermisosObjetosHibernate pO){
        List<PermisosHibernate> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_PERMISO_USUARIO_OBJETO, PermisosHibernate.class).setParameter("idU", pO.getUsuario().getId()).setParameter("idO", pO.getObjeto().getId()).getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

    public Either<Integer, List<PermisosHibernate>> getAll(PermisosUbicacionesHibernate pU){
        List<PermisosHibernate> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_PERMISO_USUARIO_UBICACION, PermisosHibernate.class).setParameter("idUs", pU.getUsuario().getId()).setParameter("idUb", pU.getUbicacion().getId()).getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

}
