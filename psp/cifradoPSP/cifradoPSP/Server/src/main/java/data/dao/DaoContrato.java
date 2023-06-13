package data.dao;

import data.model.ContratoEntity;
import data.model.UsuarioEntity;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;

import java.util.List;

public interface DaoContrato {

    List<ContratoEntity> getAllOfContratista(UsuarioEntity usuario);

    List<ContratoEntity> getAllOfSicario(UsuarioEntity usuario);

    List<ContratoEntity> getAllOfSicarioFilterEstado(UsuarioEntity usuario, Estado estado);

    ContratoEntity get(int id);

    ContratoEntity add(ContratoEntity contrato);

    boolean updateable(ContratoEntity contrato);

    void update(ContratoEntity contrato);
}
