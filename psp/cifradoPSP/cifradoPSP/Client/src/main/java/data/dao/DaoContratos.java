package data.dao;

import domain.models.Contrato;
import domain.models.Estado;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoContratos {

    Single<Either<String, Contrato>> add(Contrato contrato);

    Single<Either<String, Contrato>> update(Contrato contrato);

    Single<Either<String, List<Contrato>>> getContratosContratista(int id);

    Single<Either<String, List<Contrato>>> getContratosSicario(int id);

    Single<Either<String, List<Contrato>>> getContratosSicarioFilterEstado(int id, Estado estado);

    Single<Either<String, Contrato>> getContrato(int id);
}
