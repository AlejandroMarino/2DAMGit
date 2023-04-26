package services;

import dao.daoSpring.DaoObjectSpring;
import dao.daoSpring.DaoPermissionSpring;
import dao.daoSpring.DaoUserSpring;
import dao.daoXml.DaoXml;
import domain.modelJDBCSpring.Objeto;
import domain.modelJDBCSpring.Permiso;
import domain.modelJDBCSpring.Usuario;
import domain.modelXml.ObjetoXml;
import domain.modelXml.PermisoXml;
import domain.modelXml.UsuarioXml;
import domain.modelXml.Usuarios;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class ServicesXml {

    private DaoXml dX;
    private DaoUserSpring dU;
    private DaoObjectSpring dO;
    private DaoPermissionSpring dP;

    @Inject
    public ServicesXml(DaoXml dX, DaoUserSpring dU, DaoObjectSpring dO, DaoPermissionSpring dP) {
        this.dX = dX;
        this.dU = dU;
        this.dO = dO;
        this.dP = dP;
    }

    public Either<String, Void> generateXml() {
        Either<Integer, List<Usuario>> usuarios = dU.getAll();
        if (usuarios.isLeft()) {
            return Either.left("Error while getting users");
        } else {
            List<UsuarioXml> usuariosXml = new ArrayList<>();
            usuarios.get().forEach(usuario -> {
                Either<Integer, List<Objeto>> objetos = dO.getAll(usuario);
                List<ObjetoXml> objetosXml= new ArrayList<>();
                if (objetos.isRight()) {
                    objetos.get().forEach(objeto -> {
                        Either<Integer, List<Permiso>> permisos = dP.getAll(usuario,objeto);
                        List<PermisoXml> permisosXml = new ArrayList<>();
                        if (permisos.isRight()){
                            permisos.get().forEach(permiso -> {
                                PermisoXml p = new PermisoXml(permiso.getId(), permiso.getDescripcion());
                                permisosXml.add(p);
                            });
                        }
                        ObjetoXml o = new ObjetoXml(objeto.getId(), permisosXml);
                        objetosXml.add(o);
                    });
                }
                UsuarioXml u = new UsuarioXml(usuario.getId(), usuario.getNombre(), usuario.getFechaNacimiento(), objetosXml);
                usuariosXml.add(u);
            });
            Either<Integer, Void> xml = dX.saveXml(new Usuarios(usuariosXml));
            if (xml.isLeft()) {
                return Either.left("Error while adding xml");
            } else {
                return Either.right(null);
            }
        }
    }
}
