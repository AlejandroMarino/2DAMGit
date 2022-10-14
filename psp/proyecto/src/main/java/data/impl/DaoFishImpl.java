package data.impl;

import data.DaoFish;
import data.retrofit.ACApi;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.scene.image.Image;
import modelo.Fish;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import java.util.Map;
import java.util.Objects;

public class DaoFishImpl implements DaoFish {

    private final ACApi api;

    @Inject
    public DaoFishImpl(ACApi api) {
        this.api = api;
    }

    @Override
    public Either<String, Map<String, Fish>> getFishes() {
        try {
            return Either.right(api.getFishes().execute().body());
        } catch (Exception e) {
            return Either.left("No se ha podido conectar con la API");
        }
    }

    @Override
    public Either<String, Fish> getFish(int id) {
        try {
            return Either.right(api.getFish(id).execute().body());
        } catch (Exception e) {
            return Either.left("No se ha podido conectar con la API");
        }
    }

    @Override
    public String getIcon(Fish fish) {
        return fish.getIcon_uri();
    }

    @Override
    public Single<Either<String, Fish>> llamadaRettrofitSingle(String name) {


        return api.getFishAsync(name)
                .map(fish -> Either.right(fish).mapLeft(throwable -> "No se ha podido conectar con la API"))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, Fish> error = Either.left("Error de comunicacion");
                    if (throwable instanceof HttpException httpException  ){
                        try (ResponseBody errorBody = Objects.requireNonNull(httpException.response()).errorBody()) {

                            if (Objects.equals(errorBody.contentType(), MediaType.get("application/json"))) {
//                                Gson g = new Gson();
//                                dao.modelo.marvel.ApiError apierror = g.fromJson(((HttpException) throwable).response().errorBody().string(), dao.modelo.marvel.ApiError.class);
//                                error = Either.left(apierror.getMessage());
                            } else {
                                error = Either.left(httpException.response().message());
                            }
                        }
                    }
                    return error;
                });
    }

}
