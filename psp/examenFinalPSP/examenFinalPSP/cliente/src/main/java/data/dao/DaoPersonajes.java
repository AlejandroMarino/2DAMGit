package data.dao;

import domain.models.Personaje;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPersonajes {

    Single<Either<String, List<Personaje>>> getPersonajesArmaHabilidad(int idArma, int idHabilidad);


}
