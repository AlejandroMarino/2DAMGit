package data.model;

import common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class SicariosContratosId implements Serializable {
    @Column(name = Constants.ID_USUARIO_COLUMN)
    private int idUsuario;
    @Column(name = Constants.ID_CONTRATO_COLUMN)
    private int idContrato;
}
