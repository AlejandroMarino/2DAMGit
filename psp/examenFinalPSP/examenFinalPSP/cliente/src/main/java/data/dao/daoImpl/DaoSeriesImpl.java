package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoSeries;
import data.network.SeriesApi;
import domain.models.Serie;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoSeriesImpl extends DaoGenerics implements DaoSeries {

    private final SeriesApi seriesApi;

    @Inject
    public DaoSeriesImpl(Gson gson, SeriesApi seriesApi) {
        super(gson);
        this.seriesApi = seriesApi;
    }


    @Override
    public Single<Either<String, List<Serie>>> getAllSeries() {
        return safeSingleApicall(seriesApi.getSeries())
                .subscribeOn(Schedulers.io());
    }
}
