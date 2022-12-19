package config;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Configuration config;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {

        Connection conn = DriverManager
                .getConnection(config.getPath(), config.getUser_name(), config.getDB_password());
        System.out.println("Connected to DB");
        return conn;
    }

    public void closeConnection(Connection connArg) {
        System.out.println("Releasing all open resources ...");
        try {
            if (connArg != null) {
                connArg.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
