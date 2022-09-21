package ui.pantallas.login;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel() {
        state = new SimpleObjectProperty<>(new LoginState(false, null));
    }
}
