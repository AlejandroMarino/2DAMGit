package data.network;

import domain.models.Personaje;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PersonajesApi {

    @GET("personajes")
    Single<List<Personaje>> getPersonajesArmaFilterHabilidad(@Query("arma") int armaId,@Query("habilidad") int habilidad);
}
