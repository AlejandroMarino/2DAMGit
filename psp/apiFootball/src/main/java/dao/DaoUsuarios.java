package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.Usuario;
import dao.retrofit.AreasAPI;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DaoUsuarios extends DaoGenerics{


    public Single<Either<String, List<Area>>> updateUsuario(Usuario usu) {

        Retrofit retrofit = ConfigurationSingleton_OkHttpClient.getInstance();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        return this.safeSingleApicall(areasAPI.loadAreas())
                .map(either -> either.map(AreasRequest::getAreas));



//        try {
//            Response<Usuario> response = call.execute();
//            if (response.isSuccessful())
//            {
//                Usuario u =  response.body();
//                resultado = Either.right(u);
//            }
//            else
//            {
//                String respuesta = response.errorBody().string();
//                AtomicReference<ApiError> api = new AtomicReference<>();
//                if (response.errorBody().contentType().equals(MediaType.get("application/json"))) {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    Try.of(() -> jsonb.fromJson(respuesta,ApiError.class))
//                       .onSuccess(  apiError -> api.set(apiError))
//                       .onFailure(throwable -> api.set(ApiError.builder().message(throwable.getMessage()+"Error de parseo de la respuesta").build()))   ;
//                }
//                else
//                    api.set(ApiError.builder().message("Error de comunicacion").build());
//                resultado = Either.left(api.get());
//            }
//        }
//        catch (Exception e)
//        {
//
//            resultado= Either.left(ApiError.builder().message(e.getMessage()).build());
//        }
//
//        return resultado;
    }

    public Either<String, Usuario> delUsuario(Usuario usu) {
        Either<ApiError, Usuario> resultado = null;

        Retrofit retrofit = ConfigurationSingleton_OkHttpClient.getInstance();

        AreasAPI areasAPI = retrofit.create(AreasAPI.class);


        return safeApicall(areasAPI.delUsuario(usu));
//        Call<Usuario> call = areasAPI.delUsuario(usu);
//        try {
//            Response<Usuario> response = call.execute();
//            if (response.isSuccessful()) {
//                Usuario u = response.body();
//                resultado = Either.right(u);
//            } else {
//                String respuesta = response.errorBody().string();
//                AtomicReference<ApiError> api = new AtomicReference<>();
//                if (response.errorBody().contentType().equals(MediaType.get("application/json"))) {
//                    Jsonb jsonb = JsonbBuilder.create();
//                    Try.of(() -> jsonb.fromJson(respuesta, ApiError.class))
//                            .onSuccess(apiError -> api.set(apiError))
//                            .onFailure(throwable -> api.set(ApiError.builder().message(throwable.getMessage() + "Error de parseo de la respuesta").build()));
//                } else
//                    api.set(ApiError.builder().message("Error de comunicacion").build());
//                resultado = Either.left(api.get());
//            }
//        } catch (Exception e) {
//
//            resultado = Either.left(ApiError.builder().message(e.getMessage()).build());
//        }
//
//        return resultado;
    }
}
