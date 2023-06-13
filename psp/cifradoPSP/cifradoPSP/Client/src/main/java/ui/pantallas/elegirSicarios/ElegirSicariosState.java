package ui.pantallas.elegirSicarios;

import domain.models.Usuario;
import lombok.Data;

import java.util.List;
@Data
public class ElegirSicariosState {
    private final List<Usuario> sicarios;
    private final String message;
    private final boolean aniadidos;
}
