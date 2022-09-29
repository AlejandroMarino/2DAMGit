package data.retrofit;

import modelo.Fish;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ACApi {

    @GET("bitterling")
    Call<Fish> getFish();
}
