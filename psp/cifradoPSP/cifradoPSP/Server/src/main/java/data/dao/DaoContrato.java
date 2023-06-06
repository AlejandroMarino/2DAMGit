package data.dao;

import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;

import java.util.List;

public interface DaoContrato {

    List<Contrato> getAllOfContratista(Usuario usuario);

    List<Contrato> getAllOfSicario(Usuario usuario);

    List<Contrato> getAllOfSicarioFilterEstado(Usuario usuario, Estado estado);

    Contrato get(int id);

    Contrato add(Contrato contrato);

    boolean updateable(Contrato contrato);

    void update(Contrato contrato);
}
