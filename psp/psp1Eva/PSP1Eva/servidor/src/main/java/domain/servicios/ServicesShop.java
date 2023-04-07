package domain.servicios;

import data.DaoShop;
import domain.models.Shop;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesShop {

    private final DaoShop daoShop;

    @Inject
    public ServicesShop(DaoShop daoShop) {
        this.daoShop = daoShop;
    }

    public List<Shop> getAll() {
        return daoShop.getAll();
    }

    public Shop get(int id) {
        return daoShop.get(id);
    }

    public Shop add(Shop shop) {
        return daoShop.add(shop);
    }

    public void delete(int id) {
        daoShop.delete(id);
    }

    public Shop update(Shop shop) {
        return daoShop.update(shop);
    }

    public List<Shop> filterByName(String name) {
        List<Shop> shops = daoShop.getAll();
        return shops.stream().filter(shop -> shop.getName().contains(name)).toList();
    }
}
