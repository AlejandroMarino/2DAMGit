package data.daoImpl;


import config.DBConnectionPool;
import data.DaoShop;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import domain.models.Shop;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DaoShopImpl implements DaoShop {

    private final DBConnectionPool db;

    @Inject
    public DaoShopImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override public List<Shop> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.query("SELECT * FROM shop", BeanPropertyRowMapper.newInstance(Shop.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }
    }

    @Override public Shop get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            Shop s = jtm.queryForObject("SELECT * FROM shop WHERE id = ?", BeanPropertyRowMapper.newInstance(Shop.class), id);
            if (s == null) {
                throw new NotFoundException("Shop not found");
            } else {
                return s;
            }
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }

    }

    @Override public void delete(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM game WHERE shop_id = ?");
            stmt1.setInt(1, id);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("DELETE FROM shop WHERE id = ?");
            stmt2.setInt(1, id);
            stmt2.executeUpdate();

            con.commit();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new BaseDatosCaidaException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override public int add(Shop shop) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName("shop");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", shop.getName());
        return jdbcInsert.execute(parameters);
    }

    @Override public Shop update(Shop s) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int r = jtm.update("UPDATE shop SET name = ? WHERE id = ?", s.getName(), s.getId());
        if (r < 1)
            throw new NotFoundException("Error updating game");
        return s;
    }
}
