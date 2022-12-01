package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    private int id;
    private String nombre;
    private String nombreEquipo;
}
