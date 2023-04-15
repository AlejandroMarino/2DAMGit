package services.servicesImpl;

import dao.DaoLogin;
import domain.model.Login;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesLogin;

public class ServicesLoginImpl implements ServicesLogin {

    private final DaoLogin daoLogin;

    @Inject
    public ServicesLoginImpl(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }


    @Override
    public Either<String, Boolean> doLogin(int customerId, String password) {
        Either<Integer, Login> login = daoLogin.get(customerId);
        if (login.isLeft()) {
            return Either.left("Customer not found");
        } else {
            if (login.get().getPassword().equals(password)) {
                return Either.right(customerId == -1);
            } else {
                return Either.left("Wrong password");
            }
        }
    }

    @Override
    public Either<String, Void> register(int customerId, String password) {
        Either<Integer, Login> login = daoLogin.get(customerId);
        if (login.isLeft()) {
            if (login.getLeft() == -2) {
                daoLogin.save(new Login(customerId, password));
                return Either.right(null);
            } else {
                return Either.left("Error while registering");
            }
        } else {
            return Either.left("Customer already registered");
        }
    }
}
