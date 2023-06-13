package ui.pantallas.crearContrato;

import domain.models.Contrato;
import lombok.Data;

@Data
public class CrearContratoState {
    private final Contrato contrato;
    private final String message;
}
