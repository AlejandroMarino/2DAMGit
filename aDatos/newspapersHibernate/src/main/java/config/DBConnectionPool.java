package config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnectionPool {
    private Configuration config;
    private DataSource hikariDataSource;
    private BasicDataSource basicDataSource;

    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        hikariDataSource = getHikariPool();
        basicDataSource = getBasicPool();
    }

    public DataSource getDataSource(){
        return hikariDataSource;
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPath());
        hikariConfig.setUsername(config.getUser_name());
        hikariConfig.setPassword(config.getDB_password());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }

    private BasicDataSource getBasicPool() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(config.getUser_name());
        basicDataSource.setPassword(config.getPassword());
        basicDataSource.setUrl(config.getPath());

        return basicDataSource;
    }

    public Connection getConnection() {
        Connection con=null;
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
