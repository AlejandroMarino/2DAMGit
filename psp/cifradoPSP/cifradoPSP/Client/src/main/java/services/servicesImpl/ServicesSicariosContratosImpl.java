package services.servicesImpl;

import data.dao.DaoSicarioContrato;
import domain.models.Estado;
import domain.models.SicarioContrato;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesSicariosContratos;

import java.util.List;

public class ServicesSicariosContratosImpl implements ServicesSicariosContratos {

    private final DaoSicarioContrato dSC;

    @Inject
    public ServicesSicariosContratosImpl(DaoSicarioContrato dSC) {
        this.dSC = dSC;
    }

    @Override
    public Single<Either<String, String>> add(List<SicarioContrato> sicariosContrato) {
        return dSC.add(sicariosContrato);
    }

    @Override
    public Single<Either<String, SicarioContrato>> update(SicarioContrato sicarioContrato) {
        return dSC.update(sicarioContrato);
    }

    @Override
    public Single<Either<String, SicarioContrato>> getSicarioContrato(SicarioContrato sicarioContrato) {
        return dSC.getSicarioContrato(sicarioContrato);
    }


}
