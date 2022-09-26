package ui.pantallas.addArticles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AddArticlesViewModel {
    private final ObjectProperty<AddArticlesState> state;

    public ReadOnlyObjectProperty<AddArticlesState> getState() {
        return state;
    }

    @Inject
    public AddArticlesViewModel() {
        state = new SimpleObjectProperty<>(new AddArticlesState(null));
    }
}
