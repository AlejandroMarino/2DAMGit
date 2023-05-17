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

import java.util.List;

@Controller
public class ShopController {

    private final ServicesShop sS;
    private final ServicesGame sG;

    public ShopController(ServicesShop sS, ServicesGame sG) {
        this.sS = sS;
        this.sG = sG;
    }

    @QueryMapping
    public List<Shop> getAllShops() {
        return sS.getAll();
    }

    @QueryMapping
    public List<Shop> shopsFilterByName(@Argument String name) {
        return sS.filterByName(name);
    }

    @QueryMapping
    public Shop getShop(@Argument int id) {
        return sS.get(id);
    }

    @SchemaMapping
    public List<Game> games(Shop shop) {
        return sG.getAllOfShop(shop.getId());
    }

    @MutationMapping
    public Shop addShop(@Argument String name) {
        Shop shop = new Shop(name);
        return sS.add(shop);
    }

    @MutationMapping
    public Shop updateShop(@Argument int id, @Argument String name) {
        Shop shop = new Shop(id, name);
        return sS.update(shop);
    }

    @MutationMapping
    public Boolean deleteShop(@Argument int id) {
        return sS.delete(id);
    }
}
