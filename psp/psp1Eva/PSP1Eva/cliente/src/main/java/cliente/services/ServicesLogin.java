package cliente.services;

import domain.models.User;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

public interface ServicesLogin {

    Single<Either<String, User>> register(User user);
}
