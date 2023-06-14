package ui.pantallas.crearContrato;

import domain.model.Detalle;
import domain.models.Contrato;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesContratos;

import java.security.KeyStore;

public class CrearContratoViewModel {

    private final ServicesContratos sC;

    private final ObjectProperty<CrearContratoState> state;

    public ReadOnlyObjectProperty<CrearContratoState> getState() {
        return state;
    }

    @Inject
    public CrearContratoViewModel(ServicesContratos sC) {
        this.sC = sC;
        state = new SimpleObjectProperty<>(new CrearContratoState(null, null));
    }

    public void crearContrato(Contrato contrato, Detalle detalle, KeyStore ks) {
        sC.add(contrato, detalle, ks)
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new CrearContratoState(null, either.getLeft()));
                            } else {
                                state.setValue(new CrearContratoState(either.get(), null));
                            }
                        }
                );
    }

    public void mostrarMensaje(String s) {
        state.setValue(new CrearContratoState(null, s));
    }
}
