package servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Fish;

import java.util.List;

public interface ServiciosFish {
    Either<String, List<Fish>> getFishes();

    Either<String, Fish> getFish(int id);

    Either<String, Integer> getId(Fish fish);

    String getIcon(Fish fish);

    Single<Either<String, Fish>> llamadaRetrofitSingle(String name);

    String getImage(Fish fish);
}
