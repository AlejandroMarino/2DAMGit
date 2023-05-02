package cliente.data.network;

import cliente.ui.pantallas.login.LoginState;
import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

public interface LoginApi {

    @POST("login/register")
    Single<User> register(@Body User user);

    @GET("login")
    Single<Response<Void>> getLogin(@Header("Authorization") String basicAuthorization);


}
