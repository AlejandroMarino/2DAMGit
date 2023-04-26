package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermisosUbicacion {
    private int id;
    private Ubicacion ubicacion;
    private Usuario usuario;
    private Permiso permiso;
}
