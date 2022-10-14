package data.retrofit;

import javafx.scene.image.Image;
import modelo.Fish;
import retrofit2.Call;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;

public interface ACApi {

    @GET("fish")
    Call<Map<String, Fish>> getFishes();

    @GET("fish/{id}")
    Call<Fish> getFish(@Path("id") int id);

    @GET("fish/{name}")
    Single<Fish> getFishAsync(@Path("name") String name);
}
