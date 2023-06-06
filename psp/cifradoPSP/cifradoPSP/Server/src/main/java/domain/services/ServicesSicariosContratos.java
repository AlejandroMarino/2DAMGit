package domain.services;

import domain.models.Estado;
import domain.models.SicarioContrato;

import java.util.List;

public interface ServicesSicariosContratos {

    void add(List<SicarioContrato> sicarioContratos);

    SicarioContrato update(SicarioContrato sicarioContrato);

    Estado getEstado(SicarioContrato sicarioContrato);
}
