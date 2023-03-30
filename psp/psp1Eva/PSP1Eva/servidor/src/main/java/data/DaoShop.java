package data;


import config.DBConnectionPool;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import domain.models.Shop;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoShop {

    private final DBConnectionPool db;

    @Inject
    public DaoShop(DBConnectionPool db) {
        this.db = db;
    }

    private List<Shop> readRS(ResultSet rs) {
        List<Shop> shops = new ArrayList<>();
        try {
            while (rs.next()) {
                int shopId = rs.getInt("id");
                String name = rs.getString("name");
                Shop s = new Shop(shopId, name);
                shops.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }

    public List<Shop> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM shop");
            return readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoShop.class.getName()).log(Level.SEVERE, null, ex);
            throw new BaseDatosCaidaException("Database error");
        }
    }

    public Shop get(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM shop WHERE id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            List<Shop> shops = readRS(rs);
            if (shops.isEmpty()) {
                throw new NotFoundException("Shop not found");
            } else {
                return shops.get(0);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoShop.class.getName()).log(Level.SEVERE, null, ex);
            throw new BaseDatosCaidaException("Database error");
        }
    }

    public void delete(int id) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(db.getDataSource());
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update("DELETE FROM game WHERE id_shop = ?", id);
            jtm.update("DELETE FROM shop WHERE id = ?", id);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new BaseDatosCaidaException("Database error");
        }
    }

    public Shop add(Shop shop) {
        List<Shop> shops;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO shop (name) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, shop.getName());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            shops = readRS(rs);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }
        return shops.get(0);
    }

    public Shop update(Shop s) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE shop SET name=? WHERE id=?")) {
                con.setAutoCommit(false);
                preparedStatement.setString(1, s.getName());
                con.commit();
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    throw new NotFoundException("Error while updating shop");
                } catch (SQLException ex1) {
                    throw new BaseDatosCaidaException("Database error");
                }
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DaoShop.class.getName()).log(Level.SEVERE, null, sqle);
            throw new BaseDatosCaidaException("Database error");
        }
        return s;
    }
}
