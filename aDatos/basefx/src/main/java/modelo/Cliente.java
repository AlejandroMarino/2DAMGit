package modelo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cliente<LineaCompra> implements Comparable<Cliente> {

    private String nombre;
    private String dni;
    private List<LineaCompra> carrito;

    public Cliente(String nombre, String dni) {
        this();
        this.nombre = nombre;
        this.dni = dni;
    }

    private Cliente() {
        carrito = new ArrayList<>();
    }

    @Override
    public int compareTo(Cliente o) {
        return this.getDni().compareToIgnoreCase(o.getDni());
    }
}
