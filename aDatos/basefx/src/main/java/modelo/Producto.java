package modelo;

import lombok.Data;

@Data
public class Producto implements Comparable<Producto> {
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public int compareTo(Producto o) {
        return this.getNombre().compareToIgnoreCase(o.getNombre());
    }
}
