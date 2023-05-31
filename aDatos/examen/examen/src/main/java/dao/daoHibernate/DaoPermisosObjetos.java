package dao.daoHibernate;

import config.JPAUtil;
import domain.model.modelHibernate.PermisosObjetosHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class DaoPermisosObjetos {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoPermisosObjetos(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<Integer, Void> save(PermisosObjetosHibernate permiso){
        em = jpaUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(permiso);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            em.close();
        }
        return Either.right(null);
    }

}
