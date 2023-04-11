package data.daoImpl;

import config.DBConnectionPool;
import data.DaoGame;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import domain.models.Game;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoGameImpl implements DaoGame {
    private final DBConnectionPool db;

    @Inject
    public DaoGameImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override public List<Game> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.query("SELECT * FROM game", BeanPropertyRowMapper.newInstance(Game.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }
    }

    @Override public List<Game> getAllOfShop(int shopId) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.query("SELECT * FROM game WHERE shop_id = ?", BeanPropertyRowMapper.newInstance(Game.class), shopId);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }
    }

    @Override public Game get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.queryForObject("SELECT * FROM game WHERE id = ?", BeanPropertyRowMapper.newInstance(Game.class), id);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }

    }

    @Override public int add(Game game) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName("game");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", game.getName());
        parameters.put("description", game.getDescription());
        parameters.put("release_date", game.getReleaseDate());
        parameters.put("shop_id", game.getShopId());
        return jdbcInsert.execute(parameters);
    }

    @Override public void delete(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM game WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(e.getMessage());
        }
    }

    @Override public Game update(Game game) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int r = jtm.update("UPDATE game SET name = ?, description = ?, release_date = ?, shop_id = ? WHERE id = ?",
                game.getName(), game.getDescription(), game.getReleaseDate(), game.getShopId(), game.getId());
        if (r < 1)
            throw new NotFoundException("Error updating game");
        return game;
    }
}
