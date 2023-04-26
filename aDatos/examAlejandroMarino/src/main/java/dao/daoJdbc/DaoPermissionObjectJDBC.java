package dao.daoJdbc;

import domain.modelJDBCSpring.Objeto;
import domain.modelJDBCSpring.Permiso;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPermissionObjectJDBC {

    private DBConnectionPool db;

    @Inject
    public DaoPermissionObjectJDBC(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, Void> save(Usuario u, Objeto o, Permiso p) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO permisos_objetos (id_usuario, id_objeto, id_permiso) VALUES (?,?,?)")) {
            statement.setInt(1, u.getId());
            statement.setInt(2, o.getId());
            statement.setInt(3, p.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoPermissionObjectJDBC.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }
}
