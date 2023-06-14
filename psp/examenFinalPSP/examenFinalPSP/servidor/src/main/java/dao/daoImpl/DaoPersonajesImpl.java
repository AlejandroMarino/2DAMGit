package dao.daoImpl;

import dao.DaoPersonajes;
import domain.models.Personaje;

import java.util.ArrayList;
import java.util.List;

public class DaoPersonajesImpl implements DaoPersonajes {

    private static List<Personaje> personajes = new ArrayList<>();

    static {
        personajes.add(new Personaje(1, "Gandalf", "Mago", 1));
        personajes.add(new Personaje(2, "Aragorn", "Guerrero", 1));
        personajes.add(new Personaje(3, "Legolas", "Arquero", 1));
        personajes.add(new Personaje(4, "Paco", "paco", 2));
        personajes.add(new Personaje(5, "Pepe", "pepe", 2));
        personajes.add(new Personaje(6, "Luisma", "luisma", 3));
    }


    @Override
    public Personaje getPersonaje(int id) {
        return personajes.stream().filter(personaje -> personaje.getId() == id).findFirst().orElse(null);
    }
}