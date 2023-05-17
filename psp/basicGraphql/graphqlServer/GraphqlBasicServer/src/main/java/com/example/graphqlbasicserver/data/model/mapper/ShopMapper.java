package com.example.graphqlbasicserver.data.model.mapper;


import com.example.graphqlbasicserver.data.model.ShopEntity;
import com.example.graphqlbasicserver.domain.model.Shop;
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
