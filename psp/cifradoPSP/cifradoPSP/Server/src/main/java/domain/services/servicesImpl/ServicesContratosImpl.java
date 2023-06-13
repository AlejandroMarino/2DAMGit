package domain.services.servicesImpl;

import data.dao.DaoContrato;
import data.model.ContratoEntity;
import data.model.mapper.ContratoMapper;
import data.model.mapper.UsuariosMapper;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;
import domain.services.ServicesContratos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesContratosImpl implements ServicesContratos {

    private final DaoContrato dC;

    private final ContratoMapper cM;

    private final UsuariosMapper uM;

    @Inject
    public ServicesContratosImpl(DaoContrato dC, ContratoMapper cM, UsuariosMapper uM) {
        this.dC = dC;
        this.cM = cM;
        this.uM = uM;
    }

    @Override
    public Contrato add(Contrato contrato) {
        return cM.toContrato(dC.add(cM.toContratoEntity(contrato)));
    }

    @Override
    public Contrato update(Contrato contrato) {
        boolean updateable = dC.updateable(cM.toContratoEntity(contrato));
        if (!updateable) {
            throw new IllegalArgumentException("El contrato no se puede actualizar");
        } else {
            dC.update(cM.toContratoEntity(contrato));
            return contrato;
        }
    }

    @Override
    public List<Contrato> getAllOfContratista(Usuario usuario) {
        List<ContratoEntity> cE = dC.getAllOfContratista(uM.toUsuarioEntity(usuario));
        return cE.stream().map(cM::toContrato).toList();
    }

    @Override
    public List<Contrato> getAllOfSicario(Usuario usuario) {
        List<ContratoEntity> cE = dC.getAllOfSicario(uM.toUsuarioEntity(usuario));
        return cE.stream().map(cM::toContrato).toList();
    }

    @Override
    public List<Contrato> getAllOfSicarioFilterEstado(Usuario usuario, Estado estado) {
        List<ContratoEntity> cE = dC.getAllOfSicarioFilterEstado(uM.toUsuarioEntity(usuario), estado);
        return cE.stream().map(cM::toContrato).toList();
    }

    @Override
    public Contrato get(int id) {
        return cM.toContrato(dC.get(id));
    }
}
