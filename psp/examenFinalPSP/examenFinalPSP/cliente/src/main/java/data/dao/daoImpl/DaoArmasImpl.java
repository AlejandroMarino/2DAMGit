package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoArmas;
import data.network.ArmasApi;
import domain.models.Arma;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoArmasImpl extends DaoGenerics implements DaoArmas {

    private final ArmasApi armasApi;

    @Inject
    public DaoArmasImpl(Gson gson, ArmasApi armasApi) {
        super(gson);
        this.armasApi = armasApi;
    }

    @Override
    public Single<Either<String, List<Arma>>> getAll() {
        return safeSingleApicall(armasApi.getAll());
    }

    @Override
    public Single<Either<String, Arma>> addArma(Arma arma) {
        return safeSingleApicall(armasApi.addArma(arma));
    }

    @Override
    public Single<Either<String, Arma>> updateArma(Arma arma) {
        return safeSingleApicall(armasApi.updateArma(arma));
    }

    @Override
    public Single<Either<String, String>> deleteSinRelaciones(int idArma) {
        return safeSingleVoidApicall(armasApi.deleteArmaSinRelaciones(idArma));
    }

    @Override
    public Single<Either<String, String>> deleteConRelaciones(int idArma) {
        return safeSingleVoidApicall(armasApi.deleteArmaConRelaciones(idArma));
    }


}
