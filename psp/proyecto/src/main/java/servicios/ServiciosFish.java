package servicios;

import io.vavr.control.Either;
import modelo.Fish;

import java.util.List;

public interface ServiciosFish {
    Either<String, List<Fish>> getFishes();

    Either<String, Fish> getFish(int id);

    Either<String, Integer> getId(Fish fish);

    String getImage(Fish fish);

}
