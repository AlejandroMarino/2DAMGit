package domain.services;

import domain.models.Usuario;

public interface ServicesUsuario {
    Usuario register(Usuario usuario);

    Usuario login(Usuario usuario);

    String generateJWS(Usuario usuario);
}
