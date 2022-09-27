package ui.pantallas.Newspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class NewspaperViewModel {
    private final ObjectProperty<NewspaperState> state;

    public ReadOnlyObjectProperty<NewspaperState> getState() {
        return state;
    }

    @Inject
    public NewspaperViewModel() {
        state = new SimpleObjectProperty<>(new NewspaperState(null, null));
    }
}
