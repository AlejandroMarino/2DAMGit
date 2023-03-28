package dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Configuration;
import dao.common.DBConstants;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
@Log4j2
public class DBConnection {

    private final Configuration config;
    private final DataSource hikariDataSource;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrlDB());
        hikariConfig.setUsername(config.getUserDB());
        hikariConfig.setPassword(config.getPasswordDB());
        hikariConfig.setDriverClassName(config.getDriverDB());
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.addDataSourceProperty(DBConstants.CACHE_PREP_STMTS, true);
        hikariConfig.addDataSourceProperty(DBConstants.PREP_STMT_CACHE_SIZE, 250);
        hikariConfig.addDataSourceProperty(DBConstants.PREP_STMT_CACHE_SQL_LIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error( "ERROR CONNECTING TO THE DATABASE " + e.getMessage());
        }
        return con;
    }

    public DataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }

}
