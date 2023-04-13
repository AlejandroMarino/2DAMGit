package com.example.servidorspring.domain.services;

import com.example.servidorspring.data.GameEntityRepository;
import com.example.servidorspring.data.models.mappers.GameMapper;
import com.example.servidorspring.data.models.mappers.ShopMapper;
import domain.models.Game;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesGame {

    private final GameEntityRepository gameR;

    private final ServicesShop sShop;

    private final ShopMapper shopMapper;

    private final GameMapper gameMapper;

    public ServicesGame(GameEntityRepository gameR, ServicesShop sShop, ShopMapper shopMapper, GameMapper gameMapper) {
        this.gameR = gameR;
        this.sShop = sShop;
        this.shopMapper = shopMapper;
        this.gameMapper = gameMapper;
    }

    public List<Game> getAllGames() {
        return gameR.findAll()
                .stream()
                .map(gameMapper::toGame)
                .toList();
    }

    public Game get(int id) {
        return gameR.findById(id).map(gameMapper::toGame).orElse(null);
    }

    public Game add(Game game) {
        return gameMapper.toGame(gameR.save(gameMapper.toGameEntity(game, shopMapper.toShopEntity(sShop.get(game.getShopId())))));
    }

    public Game update(Game game) {
        return gameMapper.toGame(gameR.save(gameMapper.toGameEntity(game, shopMapper.toShopEntity(sShop.get(game.getShopId())))));
    }

    public void delete(int id) {
        gameR.deleteById(id);
    }

    public List<Game> filterByName(String name) {
        return gameR.findAll()
                .stream()
                .map(gameMapper::toGame)
                .filter(game -> game.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))
                .toList();
    }
}
