package data;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Fish;

import java.util.Map;

public interface DaoFish {
    Either<String, Map<String, Fish>> getFishes();

    Either<String, Fish> getFish(int id);

    String getIcon(Fish fish);

    Single<Either<String, Fish>> llamadaRetrofitSingle(String name);
}
