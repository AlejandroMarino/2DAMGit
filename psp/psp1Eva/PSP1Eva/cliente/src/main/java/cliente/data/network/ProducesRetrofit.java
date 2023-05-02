package cliente.data.network;

import cliente.config.Configuration;
import com.google.gson.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Gson getGson()
    {
        return  new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Gson gson, Configuration config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        return new Retrofit.Builder()
                .baseUrl(config.getPathApi())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }
    @Produces
    public GamesApi getGamesApi(Retrofit retrofit) {
        return retrofit.create(GamesApi.class);
    }

    @Produces
    public ShopsApi getShopsApi(Retrofit retrofit) {
        return retrofit.create(ShopsApi.class);
    }

    @Produces
    public LoginApi getLoginApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

}
