package ui.pantallas.login;

import asymmetric.KeyStore;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesUsuarios;

public class LoginViewModel {

    private final ServicesUsuarios sU;

    private final ObjectProperty<LoginState> state;

    private final KeyStore keyStore;

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    @Inject
    public LoginViewModel(ServicesUsuarios sU, KeyStore keyStore) {
        this.sU = sU;
        this.keyStore = keyStore;
        state = new SimpleObjectProperty<>(new LoginState(null, false, null, null));
    }

    public void login(Usuario user) {
        state.setValue(new LoginState(null, false, null, null));
        sU.login(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new LoginState(either.getLeft(), false, null, null));
                            } else {
                                Usuario usuario = either.get();
                                Either<String, java.security.KeyStore> rGetKeyStore = keyStore.getKeyStore("Client/data/" + usuario.getNombre() + ".pfx", user.getPassword());
                                if (rGetKeyStore.isLeft()) {
                                    state.setValue(new LoginState(rGetKeyStore.getLeft(), false, null, null));
                                } else {
                                    state.setValue(new LoginState(null, true, usuario, rGetKeyStore.get()));
                                }
                            }
                        }
                );
    }
}
