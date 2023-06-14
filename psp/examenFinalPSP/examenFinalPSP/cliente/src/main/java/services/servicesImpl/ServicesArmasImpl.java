package services.servicesImpl;

import data.dao.DaoArmas;
import domain.models.Arma;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesArmas;

import java.util.List;

public class ServicesArmasImpl implements ServicesArmas {

    private final DaoArmas dA;

    @Inject
    public ServicesArmasImpl(DaoArmas dA) {
        this.dA = dA;
    }

    @Override
    public Single<Either<String, List<Arma>>> getAll() {
        return dA.getAll();
    }

    @Override
    public Single<Either<String, Arma>> addArma(Arma arma) {
        return dA.addArma(arma);
    }

    @Override
    public Single<Either<String, Arma>> updateArma(Arma arma) {
        return dA.updateArma(arma);
    }

    @Override
    public Single<Either<String, String>> deleteSinRelaciones(int idArma) {
        return dA.deleteSinRelaciones(idArma);
    }

    @Override
    public Single<Either<String, String>> deleteConRelaciones(int idArma) {
        return dA.deleteConRelaciones(idArma);
    }
}
