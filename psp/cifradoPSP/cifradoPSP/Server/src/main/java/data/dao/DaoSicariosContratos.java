package data.dao;

import domain.models.SicarioContrato;
import domain.models.Estado;

public interface DaoSicariosContratos {

    Estado getEstado(SicarioContrato sicarioContrato);

    SicarioContrato add(SicarioContrato sicarioContrato);

    SicarioContrato update(SicarioContrato sicarioContrato);
}
