package domain.servicios.serviciosImpl;

import data.DaoGame;
import domain.models.Game;
import domain.servicios.ServicesGame;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesGameImpl implements ServicesGame {
    private final DaoGame daoGame;

    @Inject
    public ServicesGameImpl(DaoGame daoGame) {
        this.daoGame = daoGame;
    }

    @Override public List<Game> getAll() {
        return daoGame.getAll();
    }

    @Override public List<Game> getAllOfShop(int shopId) {
        return daoGame.getAllOfShop(shopId);
    }

    @Override public Game get(int id) {
        return daoGame.get(id);
    }

    @Override public boolean add(Game game) {
        return daoGame.add(game) == 1;
    }

    @Override public void delete(int id) {
        daoGame.delete(id);
    }

    @Override public Game update(Game g) {
        return daoGame.update(g);
    }

    @Override public List<Game> filterByName(String name) {
        List<Game> games = daoGame.getAll();
        return games.stream().filter(game -> game.getName().trim().toLowerCase().contains(name.trim().toLowerCase())).toList();
    }
}
