package ui.screens.login;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServicesLogin;

public class LoginViewModel {

    private final ServicesLogin servicesLogin;
    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin) {
        state = new SimpleObjectProperty<>(new LoginState(false, null));
        this.servicesLogin = servicesLogin;
    }

    public void login(String user, String password) {
        if(servicesLogin.login(user, password)) {
            state.setValue(new LoginState(true, null));
        } else {
            state.setValue(new LoginState(false, "Usuario o contraseña incorrectos"));
        }
    }
}
