package cliente.data.dao;

import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

public interface DaoLogin {

    Single<Either<String, User>> register(User user);

    Single<Either<String, String>> login(User user);
}
