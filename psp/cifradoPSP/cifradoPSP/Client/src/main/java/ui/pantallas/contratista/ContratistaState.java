package ui.pantallas.contratista;

import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class ContratistaState {
    private final List<Contrato> contratos;
    private final List<Usuario> sicarios;
    private final Detalle detalle;
    private final Estado estado;
    private final String message;
}
