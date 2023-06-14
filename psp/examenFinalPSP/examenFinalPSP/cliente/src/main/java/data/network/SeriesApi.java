package data.network;

import domain.models.Serie;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

import java.util.List;

public interface SeriesApi {

    @GET("series")
    Single<List<Serie>> getSeries();
}
