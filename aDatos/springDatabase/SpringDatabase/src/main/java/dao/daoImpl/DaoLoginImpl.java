package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoLogin;
import domain.model.spring.Login;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoLoginImpl implements DaoLogin {

    private DBConnectionPool db;

    @Inject
    public DaoLoginImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public Either<Integer, Login> get(String username) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Login> l = jtm.query(Queries.GET_LOGIN, BeanPropertyRowMapper.newInstance(Login.class), username);
        if (l.isEmpty())
            return Either.left(-1);
        else{
            return Either.right(l.get(0));
        }
    }

    @Override
    public void save(Login login) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jtm.getDataSource());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", login.getUsername());
            parameters.put("password", login.getPassword());
            parameters.put("role", login.getRole());
            namedTemplate.update(Queries.ADD_LOGIN, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
