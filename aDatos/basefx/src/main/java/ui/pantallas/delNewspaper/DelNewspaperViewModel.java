package ui.pantallas.delNewspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DelNewspaperViewModel {
    private final ObjectProperty<DelNewspaperState> state;

    public ReadOnlyObjectProperty<DelNewspaperState> getState() {
        return state;
    }

    @Inject
    public DelNewspaperViewModel() {
        state = new SimpleObjectProperty<>(new DelNewspaperState(null));
    }
}
