package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String nombre;
    private String password;
    private List<String> roles;

    public Usuario(String nombre, List<String> roles) {
        this.nombre = nombre;
        this.roles = roles;
    }

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }
}
