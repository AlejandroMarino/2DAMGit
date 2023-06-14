package domain.services.servicesImpl;

import dao.DaoArmasPersonaje;
import dao.DaoPersonajes;
import domain.models.ArmaPersonaje;
import domain.models.Personaje;
import domain.services.ServicesPersonaje;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class ServicesPersonajeImpl implements ServicesPersonaje {

    private final DaoArmasPersonaje dAP;

    private final DaoPersonajes dP;

    @Inject
    public ServicesPersonajeImpl(DaoArmasPersonaje dAP, DaoPersonajes dP) {
        this.dAP = dAP;
        this.dP = dP;
    }

    @Override
    public List<Personaje> getPersonajesArmaFilterHabilidad(int armaId, int habilidad) {
        List<ArmaPersonaje> armaPersonajeList = dAP.getPersonajesArmaFilterHabilidad(armaId, habilidad);
        if (armaPersonajeList != null && !armaPersonajeList.isEmpty()) {
            List<Personaje> personajes = new ArrayList<>();
            armaPersonajeList.forEach(armaPersonaje -> personajes.add(dP.getPersonaje(armaPersonaje.getPersonajeId())));
            return personajes;
        } else {
            return emptyList();
        }
    }
}
