package dao;

import domain.model.spring.Login;
import io.vavr.control.Either;

public interface DaoLogin {

    Either<Integer, Login> get(String username);

    void save(Login login);
}
