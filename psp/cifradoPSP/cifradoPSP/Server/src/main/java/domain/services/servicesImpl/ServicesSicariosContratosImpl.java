package domain.services.servicesImpl;

import data.dao.DaoSicariosContratos;
import data.model.SicarioContratoEntity;
import data.model.mapper.SicariosContratosMapper;
import domain.models.Estado;
import domain.models.SicarioContrato;
import domain.services.ServicesSicariosContratos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesSicariosContratosImpl implements ServicesSicariosContratos {

    private final DaoSicariosContratos dSC;

    private final SicariosContratosMapper sCM;

    @Inject
    public ServicesSicariosContratosImpl(DaoSicariosContratos dSC, SicariosContratosMapper sCM) {
        this.dSC = dSC;
        this.sCM = sCM;
    }

    @Override
    public void add(List<SicarioContrato> sicarioContratos) {
        List<SicarioContratoEntity> sC = sicarioContratos.stream().map(sCM::toSicariosContratosEntity).toList();
        sC.forEach(dSC::add);
    }

    @Override
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        return sCM.toSicariosContratos(dSC.update(sCM.toSicariosContratosEntity(sicarioContrato)));
    }

    @Override
    public SicarioContrato get(SicarioContrato sicarioContrato) {
        return sCM.toSicariosContratos(dSC.get(sCM.toSicariosContratosEntity(sicarioContrato)));
    }
}
