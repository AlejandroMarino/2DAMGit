package domain.services;

import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;

import java.util.List;

public interface ServicesContratos {

    Contrato add(Contrato contrato);

    Contrato update(Contrato contrato);

    List<Contrato> getAllOfContratista(Usuario usuario);

    List<Contrato> getAllOfSicario(Usuario usuario);

    List<Contrato> getAllOfSicarioFilterEstado(Usuario usuario, Estado estado);

    Contrato get(int id);

}
