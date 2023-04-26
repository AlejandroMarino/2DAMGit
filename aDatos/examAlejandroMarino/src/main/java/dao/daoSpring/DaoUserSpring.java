package dao.daoSpring;

import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoUserSpring {
    private DBConnectionPool db;

    @Inject
    public DaoUserSpring(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, List<Usuario>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            List<Usuario> u = jtm.query("SELECT * FROM usuarios", BeanPropertyRowMapper.newInstance(Usuario.class));
            return Either.right(u);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Either<Integer, Void> update(Usuario u) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int i = jtm.update("UPDATE usuarios SET nombre = ? WHERE id = ?",u.getNombre(), u.getId());
        if (i == 0){
            return Either.left(-1);
        }else {
            return Either.right(null);
        }
    }

}
