package ui.pantallas.login;

import domain.models.Tipo;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesUsuarios;

public class LoginViewModel {

    private final ServicesUsuarios sU;

    private final ObjectProperty<LoginState> state;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServicesUsuarios sU) {
        this.sU = sU;
        state = new SimpleObjectProperty<>(new LoginState(null, false, null));
    }

    public void login(Usuario user) {
        state.setValue(new LoginState(null, false, null));
        sU.login(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new LoginState(either.getLeft(), false, null));
                            } else {
                                Usuario usuario = either.get();
                                    state.setValue(new LoginState(null, true, usuario));
                            }
                        }
                );
    }
}
