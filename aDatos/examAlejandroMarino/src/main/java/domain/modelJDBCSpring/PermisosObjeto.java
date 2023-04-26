package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisosObjeto {

    private int id;
    private Usuario usuario;
    private Objeto objeto;
    private Permiso permiso;
}
