package data.network;

import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuariosApi {

    @POST("usuario/register")
    Single<Usuario> register(@Body Usuario usuario);

    @POST("usuario/login")
    Single<Response<Void>> login(@Body Usuario usuario);
}
