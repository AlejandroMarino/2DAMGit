package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permiso {
    private int id;
    private String descripcion;
}
