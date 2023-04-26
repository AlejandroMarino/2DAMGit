package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ubicacion {
    private int id;
    private String nombre;
    private String edificio;
    private String planta;
    private String puesto;
}
