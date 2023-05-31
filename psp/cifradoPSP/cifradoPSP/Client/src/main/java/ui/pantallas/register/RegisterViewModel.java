package ui.pantallas.register;


import domain.models.Usuario;
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
        state = new SimpleObjectProperty<>(new RegisterState(null, false));
    }

    public void register(Usuario user) {
        state.setValue(new RegisterState(null, false));
        servicesLogin.register(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new RegisterState(either.getLeft(), false));
                            } else {
                                state.setValue(new RegisterState(Constants.VERIFY_YOUR_ACCOUNT_WITH_THE_EMAIL_WE_HAVE_SENT_YOU, true));
                            }
                        }
                );
    }
}
