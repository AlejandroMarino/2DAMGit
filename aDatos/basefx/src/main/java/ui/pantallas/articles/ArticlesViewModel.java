package ui.pantallas.articles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ArticlesViewModel {
    private final ObjectProperty<ArticlesState> state;

    public ReadOnlyObjectProperty<ArticlesState> getState() {
        return state;
    }

    @Inject
    public ArticlesViewModel() {
        state = new SimpleObjectProperty<>(new ArticlesState(null));
    }
}
