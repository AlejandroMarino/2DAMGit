package dao.impl;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import common.ConstantsErrors;
import commonclient.Constants;
import dao.retrofit.adapters.LocalDateTimeAdapter;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.error.ApiError;
import model.error.Error;
import okhttp3.MediaType;
import retrofit2.HttpException;
import retrofit2.Response;

import java.util.Objects;

@Log4j2
abstract class DaoGenerics {
    private Moshi buildMoshi() {
        return new Moshi.Builder()
                .add(new LocalDateTimeAdapter())
                .build();
    }

    @FromJson
    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(ConstantsErrors.CONNECTION_TIMED_OUT);
                    if (throwable instanceof HttpException httpException) {
                        if (Objects.equals(Objects.requireNonNull(
                                        Objects.requireNonNull(httpException.response()).errorBody()).contentType(),
                                MediaType.get(Constants.APPLICATION_JSON))) {

                            JsonAdapter<ApiError> jsonAdapter = buildMoshi().adapter(ApiError.class);
                            ApiError apierror = jsonAdapter.fromJson(Objects.requireNonNull(
                                    Objects.requireNonNull(httpException.response()).errorBody()).string());
                            assert apierror != null;
                            error = Either.left(apierror.getMessage());
                        } else if (httpException.code() == Error.EXCEPTION_UNAUTHORIZED.getCode()) {
                            error = Either.left("Not authorized");
                        } else if (httpException.code() == Error.EXCEPTION_FORBIDDEN.getCode()) {
                            error = Either.left(ConstantsErrors.NOT_AUTHORIZED);
                        } else if (httpException.code() == Error.EXCEPTION_BAD_REQUEST.getCode()) {
                            error = Either.left("Wrong credentials");
                        }
                    } else {
                        error = Either.left(ConstantsErrors.SERVER_CONNECTION);
                    }
                    return error;
                });
    }

    public Single<Either<String, String>> safeSingleVoidApicall(Single<Response<Void>> call) {
        return call.map(response -> {
            Either<String, String> result = Either.right(Constants.NO_CONTENT).mapLeft(Object::toString);
            if (!response.isSuccessful()) {
                if (response.code() == Error.EXCEPTION_FORBIDDEN.getCode()) {
                    result = Either.left(ConstantsErrors.NOT_AUTHORIZED);
                } else {
                    JsonAdapter<ApiError> jsonAdapter = buildMoshi().adapter(ApiError.class);
                    ApiError apierror = jsonAdapter.fromJson(Objects.requireNonNull(
                            Objects.requireNonNull(response.errorBody()).string()));
                    assert apierror != null;
                    result = Either.left(apierror.getMessage());
                }
            }
            return result;
        });
    }


}
