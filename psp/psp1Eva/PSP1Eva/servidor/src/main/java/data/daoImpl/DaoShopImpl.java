package data.daoImpl;


import common.Constants;
import common.Queries;
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
            return jtm.query(Queries.GET_ALL_SHOPS, BeanPropertyRowMapper.newInstance(Shop.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        }
    }

    @Override public Shop get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            Shop s = jtm.queryForObject(Queries.GET_SHOP, BeanPropertyRowMapper.newInstance(Shop.class), id);
            if (s == null) {
                throw new NotFoundException(Constants.SHOP_NOT_FOUND);
            } else {
                return s;
            }
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        }

    }

    @Override public void delete(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt1 = con.prepareStatement(Queries.DELETE_GAME_BY_SHOP);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement(Queries.DELETE_SHOP);
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
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName(Constants.SHOP);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.NAME, shop.getName());
        return jdbcInsert.execute(parameters);
    }

    @Override public Shop update(Shop s) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int r = jtm.update(Queries.UPDATE_SHOP, s.getName(), s.getId());
        if (r < 1)
            throw new NotFoundException(Constants.ERROR_UPDATING_GAME);
        return s;
    }
}
