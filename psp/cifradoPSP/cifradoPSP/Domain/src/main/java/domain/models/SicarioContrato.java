package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SicarioContrato {
    private Usuario usuario;
    private Contrato contrato;
    private Estado estado;
    private String clave;

    public SicarioContrato(Usuario usuario, Contrato contrato) {
        this.usuario = usuario;
        this.contrato = contrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SicarioContrato that = (SicarioContrato) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(contrato, that.contrato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, contrato);
    }
}
