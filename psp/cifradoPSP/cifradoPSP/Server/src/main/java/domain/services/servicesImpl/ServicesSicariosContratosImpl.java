package domain.services.servicesImpl;

import data.dao.DaoSicariosContratos;
import domain.models.Estado;
import domain.models.SicarioContrato;
import domain.services.ServicesSicariosContratos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesSicariosContratosImpl implements ServicesSicariosContratos {

    private final DaoSicariosContratos dSC;

    @Inject
    public ServicesSicariosContratosImpl(DaoSicariosContratos dSC) {
        this.dSC = dSC;
    }

    @Override
    public void add(List<SicarioContrato> sicarioContratos) {
        sicarioContratos.forEach(dSC::add);
    }

    @Override
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        return dSC.update(sicarioContrato);
    }

    @Override
    public Estado getEstado(SicarioContrato sicarioContrato) {
        return dSC.getEstado(sicarioContrato);
    }
}
