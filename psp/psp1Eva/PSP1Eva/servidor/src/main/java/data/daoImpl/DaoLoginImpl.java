package data.daoImpl;

import config.DBConnectionPool;
import data.DaoLogin;
import domain.models.User;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
             PreparedStatement statement = con.prepareStatement("INSERT INTO user (username, password, activated, email, activation_code) VALUES (?, ?, ?, ?, ?)")) {
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
        return jtm.queryForObject("SELECT * FROM user WHERE username = ?", BeanPropertyRowMapper.newInstance(User.class), username);
    }

    @Override
    public List<String> getRoles(String username) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<String> roles = jtm.query("SELECT name FROM role r INNER JOIN userRole uR on r.id = uR.role_id WHERE uR.username = ?", BeanPropertyRowMapper.newInstance(String.class), username);
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
            rows = jtm.update("UPDATE user SET activated = true WHERE activation_code = ?", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows > 0;
    }

}
