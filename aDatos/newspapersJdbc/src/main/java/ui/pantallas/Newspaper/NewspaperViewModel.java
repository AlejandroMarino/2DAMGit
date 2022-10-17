package ui.pantallas.Newspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosNewspaper;

public class NewspaperViewModel {
    private final ObjectProperty<NewspaperState> state;

    private final ServiciosNewspaper svnp;

    public ReadOnlyObjectProperty<NewspaperState> getState() {
        return state;
    }

    @Inject
    public NewspaperViewModel(ServiciosNewspaper svnp) {
        state = new SimpleObjectProperty<>(new NewspaperState(null, null));
        this.svnp = svnp;
    }

    public void inicio() {
//        if (svnp.getAll().isLeft()) {
//            state.setValue(new NewspaperState(null, null));
//            state.setValue(new NewspaperState(null, svnp.getAll().getLeft()));
//        } else {
//            state.setValue(new NewspaperState(null, null));
//            state.setValue(new NewspaperState(svnp.getAll().get(), null));
//        }
    }
}
