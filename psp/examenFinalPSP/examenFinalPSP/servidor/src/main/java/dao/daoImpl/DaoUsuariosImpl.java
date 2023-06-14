package dao.daoImpl;

import dao.DaoUsuarios;
import domain.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DaoUsuariosImpl implements DaoUsuarios {

    private static List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void register(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario get(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;
    }
}
