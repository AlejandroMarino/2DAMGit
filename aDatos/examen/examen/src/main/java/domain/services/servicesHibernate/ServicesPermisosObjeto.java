package domain.services.servicesHibernate;

import dao.daoHibernate.DaoPermisosObjetos;
import domain.model.modelHibernate.PermisosObjetosHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServicesPermisosObjeto {
    private DaoPermisosObjetos dPO;

    @Inject
    public ServicesPermisosObjeto(DaoPermisosObjetos dPO) {
        this.dPO = dPO;
    }

    public String addPermiso(PermisosObjetosHibernate pO){
        Either<Integer, Void> r = dPO.save(pO);
        if (r.isLeft()){
            return "Error while adding";
        } else {
            return "Added";
        }
    }
}
