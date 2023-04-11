package domain.servicios.serviciosImpl;

import data.DaoShop;
import domain.models.Shop;
import domain.servicios.ServicesShop;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesShopImpl implements ServicesShop {

    private final DaoShop daoShop;

    @Inject
    public ServicesShopImpl(DaoShop daoShop) {
        this.daoShop = daoShop;
    }

    @Override public List<Shop> getAll() {
        return daoShop.getAll();
    }

    @Override public Shop get(int id) {
        return daoShop.get(id);
    }

    @Override public boolean add(Shop shop) {
        return daoShop.add(shop) == 1;
    }

    @Override public void delete(int id) {
        daoShop.delete(id);
    }

    @Override public Shop update(Shop shop) {
        return daoShop.update(shop);
    }

    @Override public List<Shop> filterByName(String name) {
        List<Shop> shops = daoShop.getAll();
        return shops.stream().filter(shop -> shop.getName().trim().toLowerCase().contains(name.trim().toLowerCase())).toList();
    }
}
