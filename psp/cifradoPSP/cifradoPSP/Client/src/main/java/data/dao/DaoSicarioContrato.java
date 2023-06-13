package data.dao;

import domain.models.Estado;
import domain.models.SicarioContrato;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoSicarioContrato {

    Single<Either<String,String>> add(List<SicarioContrato> sicariosContrato);

    Single<Either<String,SicarioContrato>> update(SicarioContrato sicarioContrato);

    Single<Either<String, SicarioContrato>> getSicarioContrato(SicarioContrato sicarioContrato);
}
