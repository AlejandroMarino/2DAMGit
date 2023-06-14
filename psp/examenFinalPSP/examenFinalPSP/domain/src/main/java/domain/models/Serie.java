package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {
    private int id;
    private String nombre;
    private LocalDate fechaDeComienzo;
    private String descripcion;
    private int numeroDeEpisodios;
}
