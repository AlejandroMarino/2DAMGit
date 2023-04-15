package dao.daoImpl;

import config.DBConnectionPool;
import dao.DaoCustomer;
import dao.DaoLogin;
import domain.model.Login;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoLoginImpl implements DaoLogin {

    private DBConnectionPool db;

    @Inject
    public DaoLoginImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public Either<Integer, Login> get(int customerId) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM login WHERE customer_id = ?")) {
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            List<Login> logins = readRS(rs);
            if (logins.isEmpty())
                return Either.left(-2);
            else{
                return Either.right(logins.get(0));
            }
        } catch (Exception e) {
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Login login) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO login (customer_id, password) VALUES (?, ?)")) {
            statement.setInt(1, login.getCustomerId());
            statement.setString(2, login.getPassword());
            statement.executeUpdate();
            return Either.right(null);
        } catch (Exception e) {
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    private List<Login> readRS(ResultSet rs) {
        List<Login> logins = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String password = rs.getString("password");
                logins.add(new Login(id, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logins;
    }
}
