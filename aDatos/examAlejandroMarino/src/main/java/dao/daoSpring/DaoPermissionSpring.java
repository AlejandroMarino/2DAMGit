package dao.daoSpring;

import domain.modelJDBCSpring.Objeto;
import domain.modelJDBCSpring.Permiso;
import domain.modelJDBCSpring.PermisosObjeto;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoPermissionSpring {

    private DBConnectionPool db;

    @Inject
    public DaoPermissionSpring(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, List<Permiso>> getAll(Usuario u, Objeto o) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            List<Permiso> l = jtm.query("SELECT p.id, p.descripcion " +
                    "FROM permisos p " +
                    "INNER JOIN permisos_ubicaciones pu on p.id = pu.id_permiso " +
                    "INNER JOIN permisos_objetos po on p.id = po.id_permiso " +
                    "INNER JOIN ubicaciones u on pu.id_ubicacion = u.id " +
                    "INNER JOIN objetos o on u.id = o.id_ubicacion " +
                    "INNER JOIN usuarios u2 on pu.id_usuario = u2.id " +
                    "WHERE u2.id = ? " +
                    "AND o.id = ? " +
                    "OR po.id_usuario = ? AND po.id_objeto = ? " +
                    "GROUP BY p.id", BeanPropertyRowMapper.newInstance(Permiso.class), u.getId(), o.getId(), u.getId(), o.getId());
            return Either.right(l);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
    }

    public Either<Integer, Permiso> get(String descripcion) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Permiso> l = jtm.query("SELECT * FROM permisos WHERE descripcion = ?", BeanPropertyRowMapper.newInstance(Permiso.class), descripcion);
        if (l.isEmpty()) {
            return Either.left(-1);
        }else {
            return Either.right(l.get(0));
        }
    }
}
