package ui.pantallas.delNewspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;
import servicios.ServiciosArticle;
import servicios.ServiciosNewspaper;
import ui.pantallas.Newspaper.NewspaperState;

public class DelNewspaperViewModel {
    private final ObjectProperty<DelNewspaperState> state;
    private final ServiciosNewspaper svnp;
    private final ServiciosArticle svart;

    public ReadOnlyObjectProperty<DelNewspaperState> getState() {
        return state;
    }

    @Inject
    public DelNewspaperViewModel(ServiciosNewspaper svnp, ServiciosArticle svart) {
        state = new SimpleObjectProperty<>(new DelNewspaperState(null,null));
        this.svnp = svnp;
        this.svart = svart;
    }

    public void inicio() {
        if (svnp.getAll().isLeft()) {
            state.setValue(new DelNewspaperState(null, null));
            state.setValue(new DelNewspaperState(null, svnp.getAll().getLeft()));
        } else {
            state.setValue(new DelNewspaperState(null, null));
            state.setValue(new DelNewspaperState(svnp.getAll().get(), null));
        }
    }

    public void delete(Newspaper n) {
            if (svnp.delete(n)){
                state.setValue(new DelNewspaperState(null, null));
                state.setValue(new DelNewspaperState(svnp.getAll().get(), null));
            } else {
                state.setValue(new DelNewspaperState(null, null));
                state.setValue(new DelNewspaperState(null, "Newspaper not deleted"));
            }
    }

    public void checkDel(Newspaper n){
        if (svnp.validoDel(n)) {
            delete(n);
        }
        else {
            //sacar alert
        }
    }
}
