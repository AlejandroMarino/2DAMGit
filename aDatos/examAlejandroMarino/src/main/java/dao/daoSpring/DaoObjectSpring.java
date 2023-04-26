package dao.daoSpring;

import domain.modelJDBCSpring.Objeto;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoObjectSpring {

    private DBConnectionPool db;

    @Inject
    public DaoObjectSpring(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, List<Objeto>> getAll(Usuario u) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Objeto> l = jtm.query("SELECT o.id " +
                "FROM objetos o " +
                "INNER JOIN permisos_objetos po on o.id = po.id_objeto " +
                "INNER JOIN permisos_ubicaciones pu on o.id_ubicacion = pu.id_ubicacion " +
                "INNER JOIN usuarios u on po.id_usuario = u.id " +
                "WHERE u.id = ? " +
                "GROUP BY o.id", new RowMapper<Objeto>() {
            @Override
            public Objeto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Objeto o = new Objeto();
                o.setId(rs.getInt("id"));
                return o;
            }
        }, u.getId());
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l);
        }
    }
}
