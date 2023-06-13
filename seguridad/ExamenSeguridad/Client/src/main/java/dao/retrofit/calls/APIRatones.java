package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.Raton;
import model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface APIRatones {
    @GET("ratones/")
    Single<List<Raton>> getRatones();

    @POST("ratones/")
    Single<Raton> addRaton(@Body Raton raton);
}
