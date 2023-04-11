package cliente.data.dao.daoImpl;

import cliente.data.dao.DaoShops;
import cliente.data.network.ShopsApi;
import com.google.gson.Gson;
import domain.models.Shop;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoShopsImpl extends DaoGenerics implements DaoShops {

    private final ShopsApi shopsApi;

    @Inject
    public DaoShopsImpl(Gson gson, ShopsApi shopsApi) {
        super(gson);
        this.shopsApi = shopsApi;
    }

    @Override
    public Single<Either<String, List<Shop>>> getAllShops() {
        return safeSingleApicall(shopsApi.getAllShops())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Shop>>> filterByName(String name) {
        return safeSingleApicall(shopsApi.filterByName(name))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Shop>> getShop(int id) {
        return safeSingleApicall(shopsApi.getShop(id))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Shop>> addShop(Shop shop) {
        return safeSingleApicall(shopsApi.addShop(shop))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, String>> deleteShop(int id) {
        return safeSingleVoidApicall(shopsApi.deleteShop(id))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Shop>> updateShop(Shop shop) {
        return safeSingleApicall(shopsApi.updateShop(shop))
                .subscribeOn(Schedulers.io());
    }
}
