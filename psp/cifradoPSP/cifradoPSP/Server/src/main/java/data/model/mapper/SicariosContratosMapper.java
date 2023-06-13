package data.model.mapper;

import data.model.SicarioContratoEntity;
import domain.models.SicarioContrato;

public class SicariosContratosMapper {

    private final UsuariosMapper uM = new UsuariosMapper();
    private final ContratoMapper cM = new ContratoMapper();

    public SicarioContrato toSicariosContratos(SicarioContratoEntity sicarioContratoEntity) {
        return new SicarioContrato(uM.toUsuario(sicarioContratoEntity.getSicario()), cM.toContrato(sicarioContratoEntity.getContrato()), sicarioContratoEntity.getEstado(), sicarioContratoEntity.getClave());
    }

    public SicarioContratoEntity toSicariosContratosEntity(SicarioContrato sicarioContrato) {
        return new SicarioContratoEntity(uM.toUsuarioEntity(sicarioContrato.getUsuario()), cM.toContratoEntity(sicarioContrato.getContrato()), sicarioContrato.getEstado(), sicarioContrato.getClave());
    }
}
