package services;

import domain.models.SicarioContrato;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesSicariosContratos {

    Single<Either<String, String>> add(List<SicarioContrato> sicariosContrato);

    Single<Either<String, SicarioContrato>> update(SicarioContrato sicarioContrato);

    Single<Either<String, SicarioContrato>> getSicarioContrato(SicarioContrato sicarioContrato);
}
