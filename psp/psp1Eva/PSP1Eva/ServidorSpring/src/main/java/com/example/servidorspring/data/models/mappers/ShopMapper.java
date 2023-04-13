package com.example.servidorspring.data.models.mappers;

import com.example.servidorspring.data.models.ShopEntity;
import domain.models.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {

    public Shop toShop(ShopEntity shop) {
        return new Shop(shop.getId(), shop.getName());
    }

    public ShopEntity toShopEntity(Shop shop) {
        return new ShopEntity(shop.getId(), shop.getName());
    }
}
