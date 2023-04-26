package dao.daoJdbc;

import domain.modelJDBCSpring.Ubicacion;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLocationJDBC {

    private DBConnectionPool db;

    @Inject
    public DaoLocationJDBC(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, Void> delete(Ubicacion u) {
        boolean error = false;
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
//             PreparedStatement statement1 = con.prepareStatement("");
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
                return Either.left(-1);
            } catch (SQLException ex) {
                Logger.getLogger(DaoLocationJDBC.class.getName()).log(Level.SEVERE, null, e);
                return Either.left(-2);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DaoLocationJDBC.class.getName()).log(Level.SEVERE, null, e);
                error = true;
            }
        }
        if (error) {
            return Either.left(-3);
        } else {
            return Either.right(null);
        }
    }
}
