package data.dao;

import domain.models.Serie;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoSeries {

    Single<Either<String, List<Serie>>> getAllSeries();
}
