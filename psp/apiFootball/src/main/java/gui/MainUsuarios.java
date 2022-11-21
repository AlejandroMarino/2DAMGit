package gui;

import com.google.gson.*;
import dao.modelo.Usuario;
import dao.modelo.marvel.ApiError;
import dao.modelo.marvel.MarvelCharacters;
import dao.retrofit.MarvelApi;
import dao.retrofit.UsuariosAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MainUsuarios {

    public static void main(String[] args) throws IOException {
        OkHttpClient clientOK;

        Retrofit retrofit;


        clientOK = new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .build();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>(){
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.toString());
                    }
                }
        ).create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/jax/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientOK)
                .build();

       UsuariosAPI marvelAPI = retrofit.create(UsuariosAPI.class);

        Response<Usuario> response = marvelAPI.deleteUsers("3").execute();

        if (response.isSuccessful())
        {
            System.out.println(response.body());
        }
        else
        {

            ApiError apierror = gson.fromJson(response.errorBody().string(), ApiError.class);

            System.out.println("error Code"+ response.code());
            System.out.println("error Code"+ response.message());
            System.out.println("error " +apierror.getMessage());
        }


    }
}
