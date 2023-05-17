package com.example.graphqlbasicserver.data.model.mapper;


import com.example.graphqlbasicserver.data.model.GameEntity;
import com.example.graphqlbasicserver.data.model.ShopEntity;
import com.example.graphqlbasicserver.domain.model.Game;
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
