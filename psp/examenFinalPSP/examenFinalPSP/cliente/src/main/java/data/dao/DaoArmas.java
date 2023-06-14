package data.dao;

import domain.models.Arma;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoArmas {

    Single<Either<String, List<Arma>>> getAll();

    Single<Either<String, Arma>> addArma(Arma arma);

    Single<Either<String, Arma>> updateArma(Arma arma);

    Single<Either<String, String>> deleteSinRelaciones(int idArma);

    Single<Either<String, String>> deleteConRelaciones(int idArma);
}
