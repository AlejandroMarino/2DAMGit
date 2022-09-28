package ui.pantallas.infoFish;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class InfoFishViewModel {
    private final ObjectProperty<InfoFishState> state;

    public ReadOnlyObjectProperty<InfoFishState> getState() {
        return state;
    }

    @Inject
    public InfoFishViewModel() {
        state = new SimpleObjectProperty<>(new InfoFishState(null));
    }
}
