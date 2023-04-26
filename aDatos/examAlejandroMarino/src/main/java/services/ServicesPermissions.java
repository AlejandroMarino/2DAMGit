package services;

import dao.daoJdbc.DaoPermissionObjectJDBC;
import dao.daoSpring.DaoPermissionSpring;
import domain.modelJDBCSpring.Objeto;
import domain.modelJDBCSpring.Permiso;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class ServicesPermissions {
    private DaoPermissionObjectJDBC dPO;
    private DaoPermissionSpring dP;

    @Inject
    public ServicesPermissions(DaoPermissionObjectJDBC dPO, DaoPermissionSpring dP) {
        this.dPO = dPO;
        this.dP = dP;
    }

    public Either<String, Void> addPermission() {
        Usuario u = new Usuario(2);
        Objeto o = new Objeto(2);
        Either<Integer, Permiso> permiso = dP.get("escritura");
        if (permiso.isLeft()) {
            return Either.left("Error while getting permission");
        } else {
            Either<Integer, Void> add = dPO.save(u, o, permiso.get());
            if (add.isLeft()) {
                return Either.left("Error while adding permission");
            } else {
                return Either.right(null);
            }
        }
    }

    public Either<String, List<Permiso>> getPermisions(){
        Usuario u = new Usuario(5);
        Objeto o = new Objeto(32);
        Either<Integer, List<Permiso>> permisos = dP.getAll(u,o);
        if (permisos.isLeft()){
            return Either.left("Error while getting permissions");
        } else {
            return Either.right(permisos.get());
        }
    }
}
