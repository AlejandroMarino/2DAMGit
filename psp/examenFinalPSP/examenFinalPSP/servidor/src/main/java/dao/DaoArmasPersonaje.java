package dao;

import domain.models.ArmaPersonaje;

import java.util.List;

public interface DaoArmasPersonaje {

    List<ArmaPersonaje> getPersonajesArmaFilterHabilidad(int armaId, int habilidad);

    List<ArmaPersonaje> getArmaPersonajeOfArma(int armaId);

    void deleteArmaPersonajeArma(int idArma);
}
