package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {
    private int id;
    private String detalle;
    private String clave;
    private Usuario usuario;
    private int habilidad;
    private String detalleFirmado;

    public Contrato(int id) {
        this.id = id;
    }

    public Contrato(Usuario usuario, int habilidad) {
        this.usuario = usuario;
        this.habilidad = habilidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
