package dao.daoHibernate;

import config.JPAUtil;
import domain.model.modelHibernate.PermisosHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoPermisosUbicacion {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoPermisosUbicacion(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

}
