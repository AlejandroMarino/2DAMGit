package dao.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import config.ConfigurationSingleton_Client;
import lombok.extern.log4j.Log4j2;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Log4j2
public class ConfigurationSingleton_OkHttpClientPruebas {
    private static OkHttpClient clientOK;

    private static Retrofit retrofit;

    private ConfigurationSingleton_OkHttpClientPruebas() {
    }

    public static synchronized Retrofit getInstance() {
        if (clientOK == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

//            CookieJar cookieJar =
//                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));


            clientOK = new OkHttpClient.Builder()
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(chain -> {
//                                Request original = chain.request();
//
//                                Request.Builder builder1 = original.newBuilder()
//                                        .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
//                                        .url(original.url().newBuilder().addQueryParameter("headToken", "adfsdf")
//                                                .addQueryParameter("ts", "1")
//
//                                                .build());
//                                Request request = builder1.build();
//                                return chain.proceed(request);

                                Request original = chain.request();

                                Request.Builder builder1 = original.newBuilder()
                                        .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
                                        .url(original.url().newBuilder()
                                                .addQueryParameter("ts","1")
                                                .addQueryParameter("apikey","a26d34b6ea64ce618360835be5888f91")
                                                .addQueryParameter("hash","073e520a55d710ef1b77df866349e689")
                                                .build());


                                Request request = builder1.build();
                                request.newBuilder().header("Authorization", Credentials.basic("user","password")).build();
                                Response response = chain.proceed(request);

                                return response;
                            }
                    )
                    .cookieJar(new JavaNetCookieJar(cookieManager))
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
                    .baseUrl("http://localhost:8080/JAX-RS-1.0-SNAPSHOT/api/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create( new ObjectMapper()
                            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                            .registerModule(new JavaTimeModule())))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(clientOK)
                    .build();
        }

        return retrofit;
    }

}
