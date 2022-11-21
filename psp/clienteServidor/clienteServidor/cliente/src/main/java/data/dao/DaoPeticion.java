package data.dao;

import domain.modelo.Newspaper;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import retrofit2.Retrofit;

import java.util.List;

public class DaoPeticion extends DaoGenerics{
    public Single<Either<String, List<Newspaper>>> updateNews(Newspaper news) {

        Retrofit retrofit = ConfigurationSingleton_OkHttpClient.getInstance();

        //llamar a la api
        AreasAPI areasAPI = retrofit.create(AreasAPI.class);

        //llamar a la peticion
        return this.safeSingleApicall(areasAPI.loadAreas())
                .map(either -> either.map(AreasRequest::getAreas));
        //mapeas la respuesta al objeto q quieras
    }
}
