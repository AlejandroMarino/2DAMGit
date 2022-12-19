package ui.screens.delNewspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;
import servicios.ServicesNewspaper;

public class DelNewspaperViewModel {
    private final ObjectProperty<DelNewspaperState> state;
    private final ServicesNewspaper svnp;

    public ReadOnlyObjectProperty<DelNewspaperState> getState() {
        return state;
    }

    @Inject
    public DelNewspaperViewModel(ServicesNewspaper svnp) {
        state = new SimpleObjectProperty<>(new DelNewspaperState(null, null));
        this.svnp = svnp;
    }

    public void inicio() {
//        if (svnp.getAll().isLeft()) {
//            state.setValue(new DelNewspaperState(null, null));
//            state.setValue(new DelNewspaperState(null, svnp.getAll().getLeft()));
//        } else {
//            state.setValue(new DelNewspaperState(null, null));
//            state.setValue(new DelNewspaperState(svnp.getAll().get(), null));
//        }
    }

    public void delete(Newspaper n) {
//        if (svnp.delete(n)) {
//            state.setValue(new DelNewspaperState(null, null));
//            state.setValue(new DelNewspaperState(svnp.getAll().get(), null));
//        } else {
//            state.setValue(new DelNewspaperState(null, null));
//            state.setValue(new DelNewspaperState(null, "Newspaper not deleted"));
//        }
    }

//    public boolean checkDel(Newspaper n) {
//        return svnp.validoDel(n);
//    }
}
