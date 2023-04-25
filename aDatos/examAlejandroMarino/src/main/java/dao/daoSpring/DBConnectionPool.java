package dao.daoSpring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Configuration;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class DBConnectionPool {

    private final Configuration config;
    private DataSource hikariDataSource = null;

    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        hikariDataSource = getDataSourceHikari();

    }

    private DataSource getDataSourceHikari() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPath());
        hikariConfig.setUsername(config.getUser_name());
        hikariConfig.setPassword(config.getDB_password());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(1);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getDataSource() {
        return hikariDataSource;
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
