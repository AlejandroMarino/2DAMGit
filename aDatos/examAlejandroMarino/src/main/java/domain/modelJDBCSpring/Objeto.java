package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Objeto {
    private int id;
    private String nombre;
    private String description;
    private String marca;
    private String tipo;
    private Ubicacion ubicacion;

    public Objeto(int id) {
        this.id = id;
    }
}
