package data.dao;

import data.model.SicarioContratoEntity;
import domain.models.Estado;

public interface DaoSicariosContratos {

    SicarioContratoEntity get(SicarioContratoEntity sicarioContrato);

    SicarioContratoEntity add(SicarioContratoEntity sicarioContrato);

    SicarioContratoEntity update(SicarioContratoEntity sicarioContrato);
}
