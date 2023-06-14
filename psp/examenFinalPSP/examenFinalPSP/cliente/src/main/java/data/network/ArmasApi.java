package data.network;

import domain.models.Arma;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ArmasApi {

    @GET("armas")
    Single<List<Arma>> getAll();

    @POST("armas")
    Single<Arma> addArma(@Body Arma arma);

    @PUT("armas")
    Single<Arma> updateArma(@Body Arma arma);

    @DELETE("armas/{id}")
    Single<Response<Void>> deleteArmaSinRelaciones(@Path("id") int id);

    @DELETE("armas")
    Single<Response<Void>> deleteArmaConRelaciones(@Query("id") int id);


}
