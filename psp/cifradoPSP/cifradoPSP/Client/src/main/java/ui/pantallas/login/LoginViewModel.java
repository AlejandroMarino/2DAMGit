package ui.pantallas.login;

import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private final ServicesLogin servicesLogin;

    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin) {
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new LoginState(null, false));
    }

    public void login(Usuario user) {
        state.setValue(new LoginState(null, false));
        servicesLogin.login(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new LoginState(either.getLeft(), false));
                            } else {
                                state.setValue(new LoginState(null, true));
                            }
                        }
                );
    }
}
