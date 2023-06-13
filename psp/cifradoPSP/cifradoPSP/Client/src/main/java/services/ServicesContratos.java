package services;

import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Estado;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesContratos {

    Single<Either<String, Contrato>> add(Contrato contrato, Detalle detalle);

    Single<Either<String, Contrato>> update(Contrato contrato, Detalle detalle);

    Single<Either<String, List<Contrato>>> getContratosContratista(int idContratista);

    Single<Either<String, List<Contrato>>> getContratosSicario(int idSicario);

    Single<Either<String, List<Contrato>>> getContratosSicarioFilterEstado(int idSicario, Estado estado);

    Single<Either<String, Contrato>> getContrato(int id);

    Detalle getDetalleContrato(String detalle);
}
