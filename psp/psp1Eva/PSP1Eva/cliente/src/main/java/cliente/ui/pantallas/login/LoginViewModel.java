package cliente.ui.pantallas.login;

import cliente.services.ServicesLogin;
import domain.models.User;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private ServicesLogin servicesLogin;

    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin) {
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new LoginState(null, false));
    }


}
