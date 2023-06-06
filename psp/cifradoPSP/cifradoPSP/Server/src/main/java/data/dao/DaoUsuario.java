package data.dao;

import domain.models.Contrato;
import domain.models.Usuario;

import java.util.List;

public interface DaoUsuario {

    Usuario get(String username);

    Usuario get(int id);

    List<Usuario> getSicarios(Contrato contrato);

    List<Usuario> getSicariosFilterHabilidad(int habilidad);

    Usuario add(Usuario usuario);
}
