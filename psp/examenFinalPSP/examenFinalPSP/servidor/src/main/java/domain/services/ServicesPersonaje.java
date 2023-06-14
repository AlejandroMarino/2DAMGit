package domain.services;

import domain.models.Personaje;

import java.util.List;

public interface ServicesPersonaje {

    List<Personaje> getPersonajesArmaFilterHabilidad(int armaId, int habilidad);
}
