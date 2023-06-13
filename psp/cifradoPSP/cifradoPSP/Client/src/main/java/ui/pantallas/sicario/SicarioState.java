package ui.pantallas.sicario;

import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Estado;
import lombok.Data;

import java.util.List;

@Data
public class SicarioState {

    private final List<Contrato> contratos;
    private final Detalle detalle;
    private final Estado estado;
    private final String message;

}
