package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raton {
    private int id;
    private String nombre;
    private int edad;

    public Raton(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}
