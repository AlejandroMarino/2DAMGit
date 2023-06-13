package data.network;

import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UsuarioApi {

    @POST("usuarios/register")
    Single<Usuario> register(@Body Usuario usuario);

    @POST("usuarios/login")
    Single<Usuario> login(@Body Usuario usuario);

    @GET("usuarios/sicarioContrato")
    Single<List<Usuario>> getSicariosContrato(@Query("contrato") int idContrato);

    @GET("usuarios/sicariosFilterHabilidad")
    Single<List<Usuario>> getSicariosFilterHabilidad(@Query("habilidad") int habilidad);

}
