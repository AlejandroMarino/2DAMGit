package data.daoImpl;

import common.Constants;
import common.Queries;
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
            return jtm.query(Queries.GET_ALL_GAMES, BeanPropertyRowMapper.newInstance(Game.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        }
    }

    @Override public List<Game> getAllOfShop(int shopId) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.query(Queries.FILTER_GAMES_BY_SHOP, BeanPropertyRowMapper.newInstance(Game.class), shopId);
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        }
    }

    @Override public Game get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return jtm.queryForObject(Queries.GET_GAME, BeanPropertyRowMapper.newInstance(Game.class), id);
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        }

    }

    @Override
    public int add(Game game) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName(Constants.GAME);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.NAME, game.getName());
        parameters.put(Constants.DESCRIPTION, game.getDescription());
        parameters.put(Constants.RELEASE_DATE, game.getReleaseDate());
        parameters.put(Constants.SHOP_ID, game.getShopId());
        return jdbcInsert.execute(parameters);
    }

    @Override public void delete(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(Queries.DELETE_GAME)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(e.getMessage());
        }
    }

    @Override public Game update(Game game) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int r = jtm.update(Queries.UPDATE_GAME,
                game.getName(), game.getDescription(), game.getReleaseDate(), game.getShopId(), game.getId());
        if (r < 1)
            throw new NotFoundException(Constants.ERROR_UPDATING_GAME);
        return game;
    }
}
