package servicios.serviciosImpl;

import data.DaoFish;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Fish;
import servicios.ServiciosFish;

import java.util.List;

public class ServiciosFishImpl implements ServiciosFish {

    private final DaoFish d;

    @Inject
    public ServiciosFishImpl(DaoFish daoFish) {
        this.d = daoFish;
    }

    @Override
    public Either<String, List<Fish>> getFishes() {
        return d.getFishes().map(fishes -> fishes.values().stream().toList());
    }

    @Override
    public Either<String, Fish> getFish(int id) {
        return d.getFish(id);
    }

    @Override
    public Either<String, Integer> getId(Fish fish) {
        if (getFishes().isRight()) {
            return Either.right(fish.getId());
        } else {
            return Either.left("No se ha podido conectar con la API");
        }
    }

    @Override
    public String getImage(Fish fish) {
        if (d.getFishes().isLeft()) {
            return "Fish not found";
        }else {
            return d.getImage(fish);
        }
    }

    @Override
    public Single<Either<String, Fish>> llamadaRetrofitSingle(String name) {
        return d.llamadaRettrofitSingle(name);
    }

}
