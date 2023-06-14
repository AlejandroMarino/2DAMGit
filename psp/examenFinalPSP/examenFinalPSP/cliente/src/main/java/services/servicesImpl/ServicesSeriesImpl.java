package services.servicesImpl;

import data.dao.DaoSeries;
import domain.models.Serie;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesSeries;

import java.util.List;

public class ServicesSeriesImpl implements ServicesSeries {

    private final DaoSeries daoSeries;

    @Inject
    public ServicesSeriesImpl(DaoSeries daoSeries) {
        this.daoSeries = daoSeries;
    }

    @Override
    public Single<Either<String, List<Serie>>> getAllSeries() {
        return daoSeries.getAllSeries();
    }
}
