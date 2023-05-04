package cliente.services.servicesImpl;

import cliente.data.dao.DaoLogin;
import cliente.services.ServicesLogin;
import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServicesLoginImpl implements ServicesLogin {

    private final DaoLogin daoLogin;

    @Inject
    public ServicesLoginImpl(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public Single<Either<String, User>> register(User user) {
        return daoLogin.register(user);
    }

    @Override
    public Single<Either<String, String>> login(User user) {
        return daoLogin.login(user);
    }


}
