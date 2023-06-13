package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.Informe;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface APIInformes {
    @GET("informes/")
    Single<List<Informe>> getInformes();

    @GET("informes/{id}")
    Single<Informe> getInforme(@Path("id") int id);

    @POST("informes/")
    Single<Informe> addInforme(@Body Informe informe);
}
