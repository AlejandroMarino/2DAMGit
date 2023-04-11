package data;

import domain.models.Shop;

import java.util.List;

public interface DaoShop {
    List<Shop> getAll();

    Shop get(int id);

    void delete(int id);

    int add(Shop shop);

    Shop update(Shop s);
}
