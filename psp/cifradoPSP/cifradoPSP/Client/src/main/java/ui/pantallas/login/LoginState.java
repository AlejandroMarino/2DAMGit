package ui.pantallas.login;

import domain.models.Usuario;
import lombok.Data;

@Data
public class LoginState {
    private final String message;
    private final boolean logged;
    private final Usuario usuario;
}
