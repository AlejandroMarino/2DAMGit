package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoPersonajes;
import data.network.PersonajesApi;
import domain.models.Personaje;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoPersonajesImpl extends DaoGenerics implements DaoPersonajes {

    private final PersonajesApi personajesApi;

    @Inject
    public DaoPersonajesImpl(Gson gson, PersonajesApi personajesApi) {
        super(gson);
        this.personajesApi = personajesApi;
    }


    @Override
    public Single<Either<String, List<Personaje>>> getPersonajesArmaHabilidad(int idArma, int idHabilidad) {
        return safeSingleApicall(personajesApi.getPersonajesArmaFilterHabilidad(idArma, idHabilidad));
    }
}
