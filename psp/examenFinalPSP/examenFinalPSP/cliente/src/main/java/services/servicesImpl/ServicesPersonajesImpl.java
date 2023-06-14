package services.servicesImpl;

import data.dao.DaoPersonajes;
import domain.models.Personaje;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesPersonajes;

import java.util.List;

public class ServicesPersonajesImpl implements ServicesPersonajes {

    private final DaoPersonajes daoPersonajes;

    @Inject
    public ServicesPersonajesImpl(DaoPersonajes daoPersonajes) {
        this.daoPersonajes = daoPersonajes;
    }

    @Override
    public Single<Either<String, List<Personaje>>> getPersonajesArmaHabilidad(int idArma, int idHabilidad) {
        return daoPersonajes.getPersonajesArmaHabilidad(idArma, idHabilidad);
    }
}
