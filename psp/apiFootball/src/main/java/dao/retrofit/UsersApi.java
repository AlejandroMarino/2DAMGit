package dao.retrofit;

import dao.modelo.jax.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UsersApi {

    @GET("users/")
    Call<List<Usuario>> getUsers();

}
