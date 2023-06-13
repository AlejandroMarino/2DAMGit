package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoSicarioContrato;
import data.network.SicarioContratoApi;
import domain.models.Estado;
import domain.models.SicarioContrato;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoSicarioContratoImpl extends DaoGenerics implements DaoSicarioContrato {

    private final SicarioContratoApi sicarioContratoApi;

    @Inject
    public DaoSicarioContratoImpl(Gson gson, SicarioContratoApi sicarioContratoApi) {
        super(gson);
        this.sicarioContratoApi = sicarioContratoApi;
    }

    @Override
    public Single<Either<String, String>> add(List<SicarioContrato> sicariosContrato) {
        return safeSingleVoidApicall(sicarioContratoApi.add(sicariosContrato))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, SicarioContrato>> update(SicarioContrato sicarioContrato) {
        return safeSingleApicall(sicarioContratoApi.update(sicarioContrato))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, SicarioContrato>> getSicarioContrato(SicarioContrato sicarioContrato) {
        return safeSingleApicall(sicarioContratoApi.get(sicarioContrato.getUsuario().getId(), sicarioContrato.getContrato().getId()))
                .subscribeOn(Schedulers.io());
    }
}
