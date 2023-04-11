package domain.servicios;

import domain.models.Game;

import java.util.List;

public interface ServicesGame {
    List<Game> getAll();

    List<Game> getAllOfShop(int shopId);

    Game get(int id);

    boolean add(Game game);

    void delete(int id);

    Game update(Game g);

    List<Game> filterByName(String name);
}
