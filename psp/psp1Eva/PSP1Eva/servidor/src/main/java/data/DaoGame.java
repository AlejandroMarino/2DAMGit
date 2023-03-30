package data;

import config.DBConnectionPool;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import domain.models.Game;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoGame {
    private DBConnectionPool db;

    @Inject
    public DaoGame(DBConnectionPool db) {
        this.db = db;
    }

    public List<Game> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.query("SELECT * FROM game", BeanPropertyRowMapper.newInstance(Game.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }
    }

    public Game get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.queryForObject("SELECT * FROM game WHERE id = ?", BeanPropertyRowMapper.newInstance(Game.class), id);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Database error");
        }

    }

    public int add(Game game) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName("game");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", game.getName());
        parameters.put("description", game.getDescription());
        parameters.put("release_date", game.getReleaseDate());
        parameters.put("shop_id", game.getShopId());
        return jdbcInsert.execute(parameters);
    }

    public void delete(int id) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM shop WHERE id = ?")) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    throw new NotFoundException("Game not found");
                } catch (SQLException ex1) {
                    throw new BaseDatosCaidaException("Database error");
                }
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DaoShop.class.getName()).log(Level.SEVERE, null, sqle);
            throw new BaseDatosCaidaException("Database error");
        }
    }

    public Game update(Game game) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int r = jtm.update("UPDATE game SET name = ?, description = ?, release_date = ?, shop_id = ? WHERE id = ?",
                game.getName(), game.getDescription(), game.getReleaseDate(), game.getShopId(), game.getId());
        if (r < 1)
            throw new NotFoundException("Error updating game");
        return game;
    }
}
