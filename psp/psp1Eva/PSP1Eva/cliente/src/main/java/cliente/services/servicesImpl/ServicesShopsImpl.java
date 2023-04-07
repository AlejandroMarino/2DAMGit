package cliente.services.servicesImpl;

import cliente.data.dao.DaoShops;
import cliente.services.ServicesShops;
import domain.models.Shop;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesShopsImpl implements ServicesShops {

    private final DaoShops daoShops;

    @Inject
    public ServicesShopsImpl(DaoShops daoShops) {
        this.daoShops = daoShops;
    }

    @Override
    public Single<Either<String, List<Shop>>> getAllShops() {
        return daoShops.getAllShops();
    }

    @Override
    public Single<Either<String, List<Shop>>> filterByName(String name) {
        return daoShops.filterByName(name);
    }

    @Override
    public Single<Either<String, Shop>> getShop(int id) {
        return daoShops.getShop(id);
    }

    @Override
    public Single<Either<String, Shop>> addShop(Shop shop) {
        return daoShops.addShop(shop);
    }

    @Override
    public Single<Either<String, String>> deleteShop(int id) {
        return daoShops.deleteShop(id);
    }

    @Override
    public Single<Either<String, Shop>> updateShop(Shop shop) {
        return daoShops.updateShop(shop);
    }
}
