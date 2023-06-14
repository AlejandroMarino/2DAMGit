package dao;

import domain.models.Usuario;

public interface DaoUsuarios {

    void register(Usuario usuario);

    Usuario get(String nombre);
}
