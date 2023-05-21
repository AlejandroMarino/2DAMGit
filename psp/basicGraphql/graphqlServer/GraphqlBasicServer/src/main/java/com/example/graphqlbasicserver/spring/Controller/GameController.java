package com.example.graphqlbasicserver.spring.Controller;

import com.example.graphqlbasicserver.domain.model.Game;
import com.example.graphqlbasicserver.domain.model.Shop;
import com.example.graphqlbasicserver.domain.services.ServicesGame;
import com.example.graphqlbasicserver.domain.services.ServicesShop;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GameController {

    private final ServicesGame sG;
    private final ServicesShop sS;

    public GameController(ServicesGame sG, ServicesShop sS) {
        this.sG = sG;
        this.sS = sS;
    }

    @QueryMapping
    public List<Game> getAllGames() {
        return sG.getAll();
    }

    @QueryMapping
    public List<Game> gamesFilterByName(@Argument String name) {
        return sG.filterByName(name);
    }

    @QueryMapping
    public Game getGame(@Argument int id) {
        return sG.get(id);
    }

    @SchemaMapping
    public Shop shop(Game game) {
        return sS.get(game.getShopId());
    }

    @MutationMapping
    public Game addGame(@Argument String name, @Argument String description, @Argument String releaseDate, @Argument int shopId) {
        Game game = new Game(name, description, LocalDate.parse(releaseDate), shopId);
        return sG.add(game);
    }

    @MutationMapping
    public Game updateGame(@Argument int id, @Argument String name, @Argument String description, @Argument String releaseDate, @Argument int shopId) {
        Game game = new Game(id, name, description, LocalDate.parse(releaseDate), shopId);
        return sG.update(game);
    }

    @MutationMapping
    public Boolean deleteGame(@Argument int id) {
        return sG.delete(id);
    }

}
