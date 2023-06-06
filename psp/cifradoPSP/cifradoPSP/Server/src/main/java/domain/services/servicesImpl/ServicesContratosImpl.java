package domain.services.servicesImpl;

import data.dao.DaoContrato;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;
import domain.services.ServicesContratos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesContratosImpl implements ServicesContratos {

    private final DaoContrato dC;

    @Inject
    public ServicesContratosImpl(DaoContrato dC) {
        this.dC = dC;
    }

    @Override
    public Contrato add(Contrato contrato) {
        return dC.add(contrato);
    }

    @Override
    public Contrato update(Contrato contrato) {
        boolean updateable = dC.updateable(contrato);
        if (!updateable) {
            throw new IllegalArgumentException("El contrato no se puede actualizar");
        } else {
            dC.update(contrato);
            return contrato;
        }
    }

    @Override
    public List<Contrato> getAllOfContratista(Usuario usuario) {
        return dC.getAllOfContratista(usuario);
    }

    @Override
    public List<Contrato> getAllOfSicario(Usuario usuario) {
        return dC.getAllOfSicario(usuario);
    }

    @Override
    public List<Contrato> getAllOfSicarioFilterEstado(Usuario usuario, Estado estado) {
        return dC.getAllOfSicarioFilterEstado(usuario, estado);
    }
}
