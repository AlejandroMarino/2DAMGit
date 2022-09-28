package ui.pantallas.inicio;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class InicioViewModel {

    private final ObjectProperty<InicioState> state;

    public ReadOnlyObjectProperty<InicioState> getState() {
        return state;
    }

    @Inject
    public InicioViewModel() {
        state = new SimpleObjectProperty<>(new InicioState(null));
    }
}
