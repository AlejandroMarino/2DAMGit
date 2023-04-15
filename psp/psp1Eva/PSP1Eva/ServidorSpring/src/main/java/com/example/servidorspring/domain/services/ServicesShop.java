package com.example.servidorspring.domain.services;

import com.example.servidorspring.data.GameEntityRepository;
import com.example.servidorspring.data.ShopEntityRepository;
import com.example.servidorspring.data.models.mappers.ShopMapper;
import domain.models.Shop;
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

    public void delete(int id) {
        gameR.deleteFromShop(id);
        shopR.deleteById(id);
    }

    public List<Shop> filterByName(String name) {
        return shopR.findAll()
                .stream()
                .map(shopMapper::toShop)
                .filter(shop -> shop.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))
                .toList();
    }
}
