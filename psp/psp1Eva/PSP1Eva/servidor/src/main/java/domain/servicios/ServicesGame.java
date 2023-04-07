package domain.servicios;

import data.DaoGame;
import domain.models.Game;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesGame {
    private final DaoGame daoGame;

    @Inject
    public ServicesGame(DaoGame daoGame) {
        this.daoGame = daoGame;
    }

    public List<Game> getAll() {
        return daoGame.getAll();
    }

    public List<Game> getAllOfShop(int shopId) {
        return daoGame.getAllOfShop(shopId);
    }

    public Game get(int id) {
        return daoGame.get(id);
    }

    public boolean add(Game game) {
        return daoGame.add(game) == 1;
    }

    public void delete(int id) {
        daoGame.delete(id);
    }

    public Game update(Game g) {
        return daoGame.update(g);
    }

    public List<Game> filterByName(String name) {
        List<Game> games = daoGame.getAll();
        return games.stream().filter(game -> game.getName().contains(name)).toList();
    }
}
