package data;

import domain.models.Game;

import java.util.List;

public interface DaoGame {
    List<Game> getAll();

    List<Game> getAllOfShop(int shopId);

    Game get(int id);

    int add(Game game);

    void delete(int id);

    Game update(Game game);
}
