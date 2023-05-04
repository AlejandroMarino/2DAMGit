package cliente.data.network;

import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    @POST("login/register")
    Single<User> register(@Body User user);

    @GET("login")
    Single<Response<Void>> login(@Query("username") String username, @Query("password") String password);


}
