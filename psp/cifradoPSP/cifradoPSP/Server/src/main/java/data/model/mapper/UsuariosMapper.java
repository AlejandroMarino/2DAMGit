package data.model.mapper;

import data.model.UsuarioEntity;
import domain.models.Usuario;

import java.util.Collections;

import static java.util.Collections.emptyList;

public class UsuariosMapper {

    public Usuario toUsuario(UsuarioEntity usuarioEntity) {
        return new Usuario(usuarioEntity.getId(), usuarioEntity.getNombre(), usuarioEntity.getTipo(), usuarioEntity.getPassword(), usuarioEntity.getHabilidad(), usuarioEntity.getClave(), emptyList());
    }

    public UsuarioEntity toUsuarioEntity(Usuario usuario) {
        return new UsuarioEntity(usuario.getId(), usuario.getNombre(), usuario.getTipo(), usuario.getPassword(), usuario.getHabilidad(), usuario.getClave());
    }
}
