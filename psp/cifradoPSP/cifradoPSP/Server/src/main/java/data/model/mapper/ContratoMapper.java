package data.model.mapper;

import data.model.ContratoEntity;
import domain.models.Contrato;

public class ContratoMapper {

    private final UsuariosMapper uM = new UsuariosMapper();

    public Contrato toContrato(ContratoEntity contratoEntity) {
        return new Contrato(contratoEntity.getId(), contratoEntity.getDetalle(), contratoEntity.getClave(), uM.toUsuario(contratoEntity.getUsuario()),contratoEntity.getHabilidad());
    }

    public ContratoEntity toContratoEntity(Contrato contrato) {
        return new ContratoEntity(contrato.getId(), contrato.getDetalle(), contrato.getClave(), contrato.getHabilidad(), uM.toUsuarioEntity(contrato.getUsuario()));
    }
}
