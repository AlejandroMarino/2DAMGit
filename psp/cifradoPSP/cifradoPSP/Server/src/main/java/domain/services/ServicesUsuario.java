package domain.services;

import domain.models.Contrato;
import domain.models.Usuario;

import java.util.List;

public interface ServicesUsuario {

    Usuario register(Usuario usuario);

    Usuario login(Usuario usuario);

    List<Usuario> getSicariosFilterHabilidad(int habilidad);

    List<Usuario> getSicariosContrato(Contrato contrato);
}
