package dao;

import com.google.gson.*;
import dao.modelo.marvel.ApiError;
import dao.modelo.marvel.MarvelCharacters;
import dao.retrofit.MarvelApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoMarvel {


    public List<dao.modelo.marvel.Character> getCharacters() {

        MarvelApi marvelAPI = ConfigurationSingleton_OkHttpClient.getInstance().create(MarvelApi.class);
        List<dao.modelo.marvel.Character> resultado = null;

        try {
            Response<MarvelCharacters> response = marvelAPI.getCharacters().execute();

            if (response.isSuccessful()) {
                resultado = response.body().getData().getCharacters();
            } else {

                Gson g = new Gson();
                ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);

            }
        } catch (Exception e) {
            Logger.getLogger("ljkhkj").log(Level.INFO, e.getMessage(), e);
        }
        return resultado;
    }

    public Single<List<dao.modelo.marvel.Character>> getCharacters(String test) {
        OkHttpClient clientOK;

        Retrofit retrofit;


        clientOK = new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(chain -> {
                            Request original = chain.request();

                            Request.Builder builder1 = original.newBuilder()
                                    .url(original.url().newBuilder()
                                            .addQueryParameter("ts", "1")
                                            .addQueryParameter("apikey", "a26d34b6ea64ce618360835be5888f91")
                                            .addQueryParameter("hash", "073e520a55d710ef1b77df866349e689")
                                            .build());
                            Request request = builder1.build();
                            return chain.proceed(request);
                        }
                )

                .build();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.toString());
                    }
                }
        ).create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();

        MarvelApi marvelAPI = retrofit.create(MarvelApi.class);

        return (Single<List<dao.modelo.marvel.Character>>) Completable.fromAction(
                        () -> {
                            Thread.sleep(5000);
                            if (test.equals("error")) throw new Exception("error de validacion");
                        })
                .andThen(marvelAPI.getCharactersRx()
                        .map(marvelCharacters -> marvelCharacters.getData().getCharacters()))
                ;

//        if (response.isSuccessful())
//        {
//            System.out.println(response.body());
//        }
//        else
//        {
//
//            Gson g = new Gson();
//            ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);
//
//            System.out.println("error Code"+ response.code());
//            System.out.println("error Code"+ response.message());
//            System.out.println("error " +apierror.getMessage());
//        }


    }
}
