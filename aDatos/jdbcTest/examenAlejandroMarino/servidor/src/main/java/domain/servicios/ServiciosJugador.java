package domain.servicios;

import data.DaoEquipo;
import domain.modelo.Equipo;
import domain.modelo.Jugador;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosJugador {

    private DaoEquipo daoEquipo;

    @Inject
    public ServiciosJugador(DaoEquipo daoEquipo) {
        this.daoEquipo = daoEquipo;
    }

    public List<Jugador> getJugadoresEquipo(String nombre) {
        Equipo e = daoEquipo.getEquipo(nombre);
        return daoEquipo.getJugadoresEquipo(e);
    }

    public Jugador add(String nombre, Jugador jugador) {
        Equipo e = daoEquipo.getEquipo(nombre);
        return daoEquipo.addJugador(e, jugador);
    }


    public Jugador delete(int id) {
        return daoEquipo.borrarJugador(id);
    }

    public Jugador update(Jugador jugador) {
        return daoEquipo.updateJugador(jugador);
    }
}
