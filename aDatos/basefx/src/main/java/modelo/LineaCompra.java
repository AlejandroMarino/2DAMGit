package modelo;

import lombok.Data;

@Data
public class LineaCompra {
    private Producto producto;
    private int cantidad;

    public LineaCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public LineaCompra(Producto producto) {
        this.producto = producto;
    }

}


