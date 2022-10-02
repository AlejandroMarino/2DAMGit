package data;

import io.vavr.control.Either;
import modelo.Fish;

import java.util.Map;

public interface DaoFish {
    Either<String, Map<String, Fish>> getFishes();

    Either<String, Fish> getFish(int id);

    String getImage(Fish fish);

}
