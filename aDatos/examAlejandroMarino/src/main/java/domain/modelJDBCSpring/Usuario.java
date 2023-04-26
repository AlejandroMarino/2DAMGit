package domain.modelJDBCSpring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(int id) {
        this.id = id;
    }
}
