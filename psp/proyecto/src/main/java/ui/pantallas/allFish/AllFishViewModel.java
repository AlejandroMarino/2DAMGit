package ui.pantallas.allFish;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AllFishViewModel {
    private final ObjectProperty<AllFishState> state;

    public ReadOnlyObjectProperty<AllFishState> getState() {
        return state;
    }

    @Inject
    public AllFishViewModel() {
        state = new SimpleObjectProperty<>(new AllFishState(null));
    }
}
