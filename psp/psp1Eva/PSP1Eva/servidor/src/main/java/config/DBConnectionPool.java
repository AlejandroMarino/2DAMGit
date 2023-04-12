package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Constants;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Named("db")
@Singleton
public class DBConnectionPool {
    private final Configuration config;
    private DataSource hikariDataSource = null;

    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
    }

    public void cargarPool(){
        hikariDataSource = getHikariPool();
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPath());
        hikariConfig.setUsername(config.getUser_name());
        hikariConfig.setPassword(config.getDB_password());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty(Constants.CACHE_PREP_STMTS, true);
        hikariConfig.addDataSourceProperty(Constants.PREP_STMT_CACHE_SIZE, 250);
        hikariConfig.addDataSourceProperty(Constants.PREP_STMT_CACHE_SQL_LIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}
