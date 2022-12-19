package ui.screens.Newspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServicesNewspaper;

public class NewspaperViewModel {
    private final ObjectProperty<NewspaperState> state;

    private final ServicesNewspaper svnp;

    public ReadOnlyObjectProperty<NewspaperState> getState() {
        return state;
    }

    @Inject
    public NewspaperViewModel(ServicesNewspaper svnp) {
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
