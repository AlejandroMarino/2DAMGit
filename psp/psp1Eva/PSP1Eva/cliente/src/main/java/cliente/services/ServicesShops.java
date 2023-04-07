package cliente.services;

import domain.models.Shop;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesShops {

    Single<Either<String, List<Shop>>> getAllShops();

    Single<Either<String, List<Shop>>> filterByName(String name);

    Single<Either<String, Shop>> getShop(int id);

    Single<Either<String, Shop>> addShop(Shop shop);

    Single<Either<String, String>> deleteShop(int id);

    Single<Either<String, Shop>> updateShop(Shop shop);
}
