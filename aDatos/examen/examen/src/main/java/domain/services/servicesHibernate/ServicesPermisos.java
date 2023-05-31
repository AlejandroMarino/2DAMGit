package domain.services.servicesHibernate;

import dao.daoHibernate.DaoObjetos;
import dao.daoHibernate.DaoPermisos;
import domain.model.modelHibernate.ObjetoHibernate;
import domain.model.modelHibernate.PermisosHibernate;
import domain.model.modelHibernate.PermisosObjetosHibernate;
import domain.model.modelHibernate.PermisosUbicacionesHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicesPermisos {
    private DaoPermisos dP;
    private DaoObjetos dO;

    @Inject
    public ServicesPermisos(DaoPermisos dP, DaoObjetos dO) {
        this.dP = dP;
        this.dO = dO;
    }

    public Either<String, Set<PermisosHibernate>> getPermisosUsuarioObjeto(PermisosObjetosHibernate pO) {
        Either<Integer, ObjetoHibernate> obj = dO.get(pO.getObjeto().getId());
        if (obj.isRight()) {
            PermisosUbicacionesHibernate pU = new PermisosUbicacionesHibernate(obj.get().getUbicacion(), pO.getUsuario());
            Either<Integer, List<PermisosHibernate>> pObjeto = dP.getAll(pO);
            Either<Integer, List<PermisosHibernate>> pUbicacion = dP.getAll(pU);
            Set<PermisosHibernate> s = new HashSet<>();
            if (pObjeto.isRight()) {
                s.addAll(pObjeto.get());
            }
            if (pUbicacion.isRight()) {
                s.addAll(pUbicacion.get());
            }
            return Either.right(s);
        }
        return Either.left("Error getting permissions");
    }
}
