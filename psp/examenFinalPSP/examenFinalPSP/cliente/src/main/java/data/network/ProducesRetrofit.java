package data.network;

import com.google.gson.*;
import config.Configuration;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

public class ProducesRetrofit {

    CacheAuthorization CacheAuthorization;

    @Inject
    public ProducesRetrofit(data.network.CacheAuthorization cacheAuthorization) {
        CacheAuthorization = cacheAuthorization;
    }

    @Produces
    @Singleton
    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Gson gson, Configuration config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .addInterceptor(new AuthorizationInterceptor(CacheAuthorization))
                .build();

        return new Retrofit.Builder()
                .baseUrl(config.getPathApi())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }

    @Produces
    public ArmasApi getArmasApi(Retrofit retrofit) {
        return retrofit.create(ArmasApi.class);
    }

    @Produces
    public PersonajesApi getPersonajesApi(Retrofit retrofit) {
        return retrofit.create(PersonajesApi.class);
    }

    @Produces
    public SeriesApi getSeriesApi(Retrofit retrofit) {
        return retrofit.create(SeriesApi.class);
    }

    @Produces
    public UsuariosApi getUsuariosApi(Retrofit retrofit) {
        return retrofit.create(UsuariosApi.class);
    }

}
