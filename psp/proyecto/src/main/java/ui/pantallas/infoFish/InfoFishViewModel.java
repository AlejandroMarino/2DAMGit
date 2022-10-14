package ui.pantallas.infoFish;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import servicios.ServiciosFish;

public class InfoFishViewModel {
    private final ServiciosFish svf;
    private final ObjectProperty<InfoFishState> state;

    public ReadOnlyObjectProperty<InfoFishState> getState() {
        return state;
    }

    @Inject
    public InfoFishViewModel(ServiciosFish svf) {
        this.svf = svf;
        state = new SimpleObjectProperty<>(new InfoFishState(null, null));
    }

    public void inicio(int id) {
        if (svf.getFish(id).isLeft()) {
            state.setValue(new InfoFishState(null, svf.getFish(id).getLeft()));
        } else {
            state.setValue(new InfoFishState(svf.getFish(id).get(), null));
        }
    }

    public String getFishLogo(int id) {
        if (svf.getFish(id).isLeft()) {
            state.setValue(new InfoFishState(null, svf.getFish(id).getLeft()));
            return null;
        } else {
            state.setValue(new InfoFishState(svf.getFish(id).get(), null));
            return svf.getIcon(svf.getFish(id).get());
        }
    }

}
