package services;

import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

public interface ServicesUsuarios {

    Single<Either<String, Usuario>> register(Usuario usuario);

    Single<Either<String, String>> login(Usuario usuario);
}
