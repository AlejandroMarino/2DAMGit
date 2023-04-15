package services;

import io.vavr.control.Either;

public interface ServicesLogin {

    Either<String, Boolean> doLogin(int customerId, String password);

    Either<String, Void> register(int customerId, String password);
}
