package com.example.servidorspring.data.models.mappers;

import com.example.servidorspring.data.models.GameEntity;
import com.example.servidorspring.data.models.ShopEntity;
import domain.models.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game toGame(GameEntity game) {
        return new Game(game.getId(), game.getName(), game.getDescription(), game.getReleaseDate(), game.getShop().getId());
    }

    public GameEntity toGameEntity(Game game, ShopEntity shop) {
        return new GameEntity(game.getId(), game.getName(), game.getDescription(), game.getReleaseDate(), shop);
    }
}
