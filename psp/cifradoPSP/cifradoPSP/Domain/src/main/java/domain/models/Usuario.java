package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private Tipo tipo;
    private String password;
    private int habilidad;
    private List<Contrato> contratos;

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(String nombre, Tipo tipo, String password, int habilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.password = password;
        this.habilidad = habilidad;
    }

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
