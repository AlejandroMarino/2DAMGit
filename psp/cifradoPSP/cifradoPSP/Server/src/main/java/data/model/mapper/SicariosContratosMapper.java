package data.model.mapper;

import data.model.SicariosContratosEntity;
import domain.models.SicarioContrato;

public class SicariosContratosMapper {

    private final UsuariosMapper uM = new UsuariosMapper();
    private final ContratoMapper cM = new ContratoMapper();

    public SicarioContrato toSicariosContratos(SicariosContratosEntity sicariosContratosEntity) {
        return new SicarioContrato(uM.toUsuario(sicariosContratosEntity.getSicario()), cM.toContrato(sicariosContratosEntity.getContrato()), sicariosContratosEntity.getEstado(), sicariosContratosEntity.getClave());
    }

    public SicariosContratosEntity toSicariosContratosEntity(SicarioContrato sicarioContrato) {
        return new SicariosContratosEntity(uM.toUsuarioEntity(sicarioContrato.getUsuario()), cM.toContratoEntity(sicarioContrato.getContrato()), sicarioContrato.getEstado(), sicarioContrato.getClave());
    }
}
