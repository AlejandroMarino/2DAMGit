package dao.retrofit;

import dao.modelo.AreasRequest;
import dao.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface UsuariosAPI {

    @GET("users/")
    Call<List<Usuario>> getUsers();

    @DELETE("users/")
    Call<Usuario> deleteUsers(@Query("id") String id);
}
