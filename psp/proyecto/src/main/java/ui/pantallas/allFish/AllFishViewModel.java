package ui.pantallas.allFish;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Fish;
import servicios.ServiciosFish;

public class AllFishViewModel {
    private final ServiciosFish svf;
    private final ObjectProperty<AllFishState> state;

    public ReadOnlyObjectProperty<AllFishState> getState() {
        return state;
    }

    @Inject
    public AllFishViewModel(ServiciosFish svf) {
        this.svf = svf;

        state = new SimpleObjectProperty<>(new AllFishState(null, null));
    }

    public int getFishId(Fish fish) {
        if (svf.getId(fish).isLeft()) {
            state.setValue(new AllFishState(null, svf.getId(fish).getLeft()));
            return 0;
        } else {
            return svf.getId(fish).get();
        }
    }

    public void inicio() {
        if (svf.getFishes().isLeft()) {
            state.setValue(new AllFishState(null, svf.getFishes().getLeft()));
        } else {
            state.setValue(new AllFishState(svf.getFishes().get(), null));
        }
    }
}
