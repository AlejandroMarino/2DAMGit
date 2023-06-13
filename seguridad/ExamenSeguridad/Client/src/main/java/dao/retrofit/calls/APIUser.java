package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.User;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface APIUser {
    @GET("users/" + "login/")
    Single<User> getValidatedUser(@Query("nombre") String nombre, @Query("password") String password);

}
