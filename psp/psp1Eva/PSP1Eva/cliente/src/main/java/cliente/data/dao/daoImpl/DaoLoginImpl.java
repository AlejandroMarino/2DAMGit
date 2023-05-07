package cliente.data.dao.daoImpl;

import cliente.common.Constants;
import cliente.data.dao.DaoLogin;
import cliente.data.network.CacheAuthorization;
import cliente.data.network.LoginApi;
import com.google.gson.Gson;
import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Response;


public class DaoLoginImpl extends DaoGenerics implements DaoLogin {

    private final CacheAuthorization cacheAuthorization;

    private final LoginApi loginApi;

    @Inject
    public DaoLoginImpl(Gson gson, CacheAuthorization cacheAuthorization, LoginApi loginApi) {
        super(gson);
        this.cacheAuthorization = cacheAuthorization;
        this.loginApi = loginApi;
    }

    @Override
    public Single<Either<String, User>> register(User user) {
        return safeSingleApicall(loginApi.register(user))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, String>> login(User user) {
        Single<Response<Void>> call = loginApi.login(user.getUsername(), user.getPassword());
        return call.map(response -> {
            var retorno = Either.right(Constants.OK).mapLeft(Object::toString);
            if (response.isSuccessful()) {
                String jwt = response.headers().get(Constants.AUTHORIZATION);
                cacheAuthorization.setJwt(jwt);
            } else {
                retorno = Either.left(response.errorBody().toString());
            }
            return retorno;
        }).subscribeOn(Schedulers.io());
    }
}
