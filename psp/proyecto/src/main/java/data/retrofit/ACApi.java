package data.retrofit;

import modelo.Fish;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;

public interface ACApi {

    @GET("fish")
    Call<Map<String, Fish>> getFishes();

    @GET("fish/{id}")
    Call<Fish> getFish(@Path("id") int id);
}
