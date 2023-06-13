package data.dao;

import data.model.ContratoEntity;
import data.model.UsuarioEntity;

import java.util.List;

public interface DaoUsuario {

    UsuarioEntity get(String username);

    UsuarioEntity get(int id);

    List<UsuarioEntity> getSicarios(int idContrato);

    List<UsuarioEntity> getSicariosFilterHabilidad(int habilidad);

    UsuarioEntity add(UsuarioEntity usuario);
}
