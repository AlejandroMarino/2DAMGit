package ui.pantallas.register;


import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesUsuarios;

public class RegisterViewModel {

    private final ServicesUsuarios sU;

    private final ObjectProperty<RegisterState> state;

    public ReadOnlyObjectProperty<RegisterState> getState() {
        return state;
    }

    @Inject
    public RegisterViewModel(ServicesUsuarios sU) {
        this.sU = sU;
        state = new SimpleObjectProperty<>(new RegisterState(null, false));
    }

    public void register(Usuario user) {
        state.setValue(new RegisterState(null, false));
        sU.register(user)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new RegisterState(either.getLeft(), false));
                            } else {
                                state.setValue(new RegisterState("registered", true));
                            }
                        }
                );
    }

    public void mostrarMensaje(String mensaje) {
        state.setValue(new RegisterState(mensaje, false));
    }
}
