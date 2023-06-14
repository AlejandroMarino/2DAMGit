package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmaPersonaje {

    private int armaId;
    private int personajeId;
    private int habilidad;
}
