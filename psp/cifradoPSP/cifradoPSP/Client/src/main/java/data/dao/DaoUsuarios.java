package data.dao;

import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoUsuarios {

    Single<Either<String, Usuario>> register(Usuario usuario);

    Single<Either<String, Usuario>> login(Usuario usuario);

    Single<Either<String, List<Usuario>>> getSicariosOfContrato(int idContrato);

    Single<Either<String, List<Usuario>>> getSicariosFilterHabilidad(int habilidad);




}
