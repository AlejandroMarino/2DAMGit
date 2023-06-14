package dao.daoImpl;

import dao.DaoArmasPersonaje;
import domain.models.ArmaPersonaje;

import java.util.ArrayList;
import java.util.List;

public class DaoArmasPersonajeImpl implements DaoArmasPersonaje {

    private static List<ArmaPersonaje> armasPersonaje = new ArrayList<>();

    static {
        armasPersonaje.add(new ArmaPersonaje(1, 2, 6));
        armasPersonaje.add(new ArmaPersonaje(2, 3, 10));
        armasPersonaje.add(new ArmaPersonaje(3, 1, 9));
        armasPersonaje.add(new ArmaPersonaje(4, 4, 6));
        armasPersonaje.add(new ArmaPersonaje(4, 5, 3));
        armasPersonaje.add(new ArmaPersonaje(5, 6, 10));
    }


    @Override
    public List<ArmaPersonaje> getPersonajesArmaFilterHabilidad(int armaId, int habilidad) {
        return armasPersonaje.stream()
                .filter(armaPersonaje -> armaPersonaje.getArmaId() == armaId && armaPersonaje.getHabilidad() >= habilidad).toList();
    }

    @Override
    public List<ArmaPersonaje> getArmaPersonajeOfArma(int armaId) {
        return armasPersonaje.stream()
                .filter(armaPersonaje -> armaPersonaje.getArmaId() == armaId).toList();
    }

    @Override
    public void deleteArmaPersonajeArma(int idArma) {
        armasPersonaje.removeIf(armaPersonaje -> armaPersonaje.getArmaId() == idArma);
    }
}
