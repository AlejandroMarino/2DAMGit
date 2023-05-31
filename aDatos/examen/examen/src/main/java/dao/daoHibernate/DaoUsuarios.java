package dao.daoHibernate;

import common.Constants;
import config.JPAUtil;
import domain.model.modelHibernate.ObjetoHibernate;
import domain.model.modelHibernate.UbicacionHibernate;
import domain.model.modelHibernate.UsuarioHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoUsuarios {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoUsuarios(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, List<UsuarioHibernate>> getAll(UbicacionHibernate u){
        List<UsuarioHibernate> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_USUARIOSUBICACION, UsuarioHibernate.class).setParameter("id", u.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

    public Either<Integer, List<UsuarioHibernate>> getAll(ObjetoHibernate o){
        List<UsuarioHibernate> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(Constants.HQL_GET_USUARIOS_PERMISOS_OBJETOS, UsuarioHibernate.class).setParameter("id",o.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(l);
    }

}
