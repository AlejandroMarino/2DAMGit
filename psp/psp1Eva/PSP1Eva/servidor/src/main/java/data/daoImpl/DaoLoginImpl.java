package data.daoImpl;

import common.Constants;
import common.Queries;
import config.DBConnectionPool;
import data.DaoLogin;
import domain.models.User;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;

public class DaoLoginImpl implements DaoLogin {

    private final DBConnectionPool db;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public DaoLoginImpl(DBConnectionPool db, Pbkdf2PasswordHash passwordHash) {
        this.db = db;
        this.passwordHash = passwordHash;
    }

    @Override
    public User register(User user) {
        try (Connection con = db.getDataSource().getConnection();
             PreparedStatement statement = con.prepareStatement(Queries.ADD_USER)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, passwordHash.generate(user.getPassword().toCharArray()));
            statement.setBoolean(3, user.isActivated());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getActivationCode());
            statement.executeUpdate();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(String username) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        return jtm.queryForObject(Queries.GET_USER, BeanPropertyRowMapper.newInstance(User.class), username);
    }

    @Override
    public List<String> getRoles(String username) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<String> roles = jtm.query(Queries.GET_ROLES_OF_USER, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(Constants.NAME);
            }
        }, username);
        if (roles.isEmpty()) {
            return emptyList();
        } else {
            return roles;
        }
    }

    @Override
    public boolean validate(String code) {
        int rows = 0;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            rows = jtm.update(Queries.ACTIVATE_USER, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows > 0;
    }

}
