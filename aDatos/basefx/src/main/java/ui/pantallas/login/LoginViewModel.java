package ui.pantallas.login;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosLogin;

public class LoginViewModel {

    private final ServiciosLogin serviciosLogin;
    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServiciosLogin serviciosLogin) {
        state = new SimpleObjectProperty<>(new LoginState(false, null));
        this.serviciosLogin = serviciosLogin;
    }

    public void login(String user, String password) {
        if(serviciosLogin.login(user, password)) {
            state.setValue(new LoginState(true, null));
        } else {
            state.setValue(new LoginState(false, "Usuario o contraseña incorrectos"));
        }
    }
}
