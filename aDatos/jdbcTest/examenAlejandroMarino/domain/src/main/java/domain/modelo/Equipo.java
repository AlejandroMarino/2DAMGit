package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private String nombre;
    private String entrenador;
    private List<Jugador> jugadores;
}
