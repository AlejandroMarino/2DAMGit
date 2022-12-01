package data;

import domain.modelo.Equipo;
import domain.modelo.Jugador;
import domain.modelo.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoEquipo {
    public static List<Equipo> equipos = new ArrayList<>();

    static {
        List<Jugador> jugadoresM = new ArrayList<>();
        jugadoresM.add(new Jugador(1, "Courtois", "Madrid"));
        jugadoresM.add(new Jugador(2, "Vinicius", "Madrid"));
        jugadoresM.add(new Jugador(3, "Bencema", "Madrid"));

        List<Jugador> jugadoresB = new ArrayList<>();
        jugadoresB.add(new Jugador(4, "Pedri", "Barcelona"));
        jugadoresB.add(new Jugador(5, "Gavi", "Barcelona"));
        jugadoresB.add(new Jugador(6, "Ansu", "Barcelona"));

        equipos.add(new Equipo("Madrid", "Angelote", jugadoresM));
        equipos.add(new Equipo("Barcelona", "Xavi", jugadoresB));
    }


    public List<Equipo> getEquipos() {
        return equipos;
    }

    public Equipo getEquipo(String nombre) {
        Equipo e = equipos.stream().filter(equipo -> equipo.getNombre().equals(nombre)).findFirst().orElse(null);
        if (e != null) {
            return e;
        } else {
            throw new NotFoundException("no se ha encontrado el equipo");
        }
    }

    public Equipo addEquipo(Equipo equipo) {
        equipos.add(equipo);
        return equipo;
    }

    public List<Jugador> getJugadoresEquipo(Equipo equipo) {
        return equipo.getJugadores();
    }

    public Jugador addJugador(Equipo equipo, Jugador jugador) {
        equipo.getJugadores().add(jugador);
        return jugador;
    }


    public Jugador updateJugador(Jugador jugador) {
        List<Jugador> jugadores = equipos.stream().flatMap(equipo -> equipo.getJugadores().stream()).toList();
        Jugador j = jugadores.stream()
                .filter(jugador1 -> jugador1.getId() == jugador.getId()).findFirst().orElse(null);
        if (j != null) {
            j = jugador;
        } else {
            throw new NotFoundException("No se ha encontrado el jugador");
        }
        return j;
    }

    public Jugador borrarJugador(int id) {
        List<Jugador> jugadores = equipos.stream().flatMap(equipo -> equipo.getJugadores().stream()).toList();
        Jugador j = jugadores.stream()
                .filter(jugador1 -> jugador1.getId() == id).findFirst().orElse(null);
        Equipo e = equipos.stream().filter(equipo -> Objects.equals(equipo.getNombre(), j.getNombreEquipo())).findFirst().orElse(null);
        if (e != null) {
            e.getJugadores().remove(j);
        } else {
            throw new NotFoundException("no se ha encontrado el jugador");
        }
        return j;
    }

}
