package com.example.graphqlbasicserver.domain.services;


import com.example.graphqlbasicserver.data.GameEntityRepository;
import com.example.graphqlbasicserver.data.ShopEntityRepository;
import com.example.graphqlbasicserver.data.model.mapper.ShopMapper;
import com.example.graphqlbasicserver.domain.model.Shop;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ServicesShop {

    private final ShopEntityRepository shopR;

    private final GameEntityRepository gameR;

    private final ShopMapper shopMapper;

    public ServicesShop(ShopEntityRepository shopR, GameEntityRepository gameR, ShopMapper shopMapper) {
        this.shopR = shopR;
        this.gameR = gameR;
        this.shopMapper = shopMapper;
    }

    public List<Shop> getAll() {
        return shopR.findAll()
                .stream()
                .map(shopMapper::toShop)
                .toList();
    }

    public Shop get(int id) {
        return shopR.findById(id).map(shopMapper::toShop).orElse(null);
    }

    public Shop add(Shop shop) {
        return shopMapper.toShop(shopR.save(shopMapper.toShopEntity(shop)));
    }

    public Shop update(Shop shop) {
        return shopMapper.toShop(shopR.save(shopMapper.toShopEntity(shop)));
    }

    public boolean delete(int id) {
        try {
            gameR.deleteFromShop(id);
            shopR.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Shop> filterByName(String name) {
        return shopR.findAll()
                .stream()
                .map(shopMapper::toShop)
                .filter(shop -> shop.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))
                .toList();
    }
}
