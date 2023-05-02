package cliente.ui.pantallas.register;

import cliente.services.ServicesLogin;
import cliente.ui.pantallas.login.LoginState;
import domain.models.User;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RegisterViewModel {

    private final ServicesLogin servicesLogin;

    private final ObjectProperty<RegisterState> state;

    public ReadOnlyObjectProperty<RegisterState> getState() {
        return state;
    }

    @Inject
    public RegisterViewModel(ServicesLogin servicesLogin) {
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new RegisterState(null,false));
    }

    public void register(User user) {
        state.setValue(new RegisterState(null,false));
        servicesLogin.register(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new RegisterState(either.getLeft(),false));
                            } else {
                                state.setValue(new RegisterState("Verify your account with the email we have sent you",true));
                            }
                        }
                );
    }
}
