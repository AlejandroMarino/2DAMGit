package domain.servicios;

import data.DaoEquipo;
import domain.modelo.Equipo;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosEquipo {
    private final DaoEquipo daoEquipo;

    @Inject
    public ServiciosEquipo(DaoEquipo daoEquipo) {
        this.daoEquipo = daoEquipo;
    }

    public List<Equipo> getAll() {
        return daoEquipo.getEquipos();
    }

    public Equipo get(String nombre) {
        return daoEquipo.getEquipo(nombre);
    }

    public Equipo add(Equipo equipo) {
        return daoEquipo.addEquipo(equipo);
    }
}
