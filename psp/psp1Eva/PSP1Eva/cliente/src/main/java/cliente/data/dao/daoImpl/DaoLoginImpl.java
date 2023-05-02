package cliente.data.dao.daoImpl;

import cliente.data.dao.DaoLogin;
import cliente.data.network.LoginApi;
import com.google.gson.Gson;
import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class DaoLoginImpl extends DaoGenerics implements DaoLogin {

    private final LoginApi loginApi;

    @Inject
    public DaoLoginImpl(Gson gson, LoginApi loginApi) {
        super(gson);
        this.loginApi = loginApi;
    }

    @Override
    public Single<Either<String, User>> register(User user) {
        return safeSingleApicall(loginApi.register(user))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, String>> getLogin(String headerBasic ){
        return safeSingleVoidApicall(loginApi.getLogin(headerBasic))
                .subscribeOn(Schedulers.io());
    }
}
