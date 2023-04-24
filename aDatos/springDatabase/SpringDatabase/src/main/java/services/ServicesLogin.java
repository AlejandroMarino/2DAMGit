package services;

import domain.model.spring.Login;
import io.vavr.control.Either;

public interface ServicesLogin {

    Either<String, Boolean> doLogin(Login login);

    void register(Login login);
}
