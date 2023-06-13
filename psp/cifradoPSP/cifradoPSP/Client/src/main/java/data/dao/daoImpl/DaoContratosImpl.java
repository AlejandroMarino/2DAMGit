package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoContratos;
import data.network.ContratoApi;
import domain.models.Contrato;
import domain.models.Estado;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoContratosImpl extends DaoGenerics implements DaoContratos {

    private final ContratoApi contratoApi;

    @Inject
    public DaoContratosImpl(Gson gson, ContratoApi contratoApi) {
        super(gson);
        this.contratoApi = contratoApi;
    }

    @Override
    public Single<Either<String, Contrato>> add(Contrato contrato) {
        return safeSingleApicall(contratoApi.add(contrato))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Contrato>> update(Contrato contrato) {
        return safeSingleApicall(contratoApi.update(contrato))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosContratista(int id) {
        return safeSingleApicall(contratoApi.getContratosContratista(id))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosSicario(int id) {
        return safeSingleApicall(contratoApi.getContratosSicario(id))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosSicarioFilterEstado(int id, Estado estado) {
        return safeSingleApicall(contratoApi.getContratosSicarioFilterEstado(id, estado))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Contrato>> getContrato(int id) {
        return safeSingleApicall(contratoApi.getContrato(id))
                .subscribeOn(Schedulers.io());
    }
}
