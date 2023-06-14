package ui.pantallas.login;

import domain.models.Usuario;
import lombok.Data;

import java.security.KeyStore;

@Data
public class LoginState {
    private final String message;
    private final boolean logged;
    private final Usuario usuario;
    private final KeyStore keyStore;
}
