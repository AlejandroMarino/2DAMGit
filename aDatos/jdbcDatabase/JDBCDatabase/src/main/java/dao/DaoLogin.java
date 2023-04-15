package dao;

import domain.model.Login;
import io.vavr.control.Either;

public interface DaoLogin {

    Either<Integer, Login> get(int customerId);

    Either<Integer, Void> save(Login login);
}
