package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detalle {
    private String titulo;
    private String objetivo;
    private Double precio;
    private int habilidad;
}
