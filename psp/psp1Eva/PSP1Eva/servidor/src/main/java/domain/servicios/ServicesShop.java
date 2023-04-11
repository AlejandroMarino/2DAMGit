package domain.servicios;

import domain.models.Shop;

import java.util.List;

public interface ServicesShop {
    List<Shop> getAll();

    Shop get(int id);

    boolean add(Shop shop);

    void delete(int id);

    Shop update(Shop shop);

    List<Shop> filterByName(String name);
}
