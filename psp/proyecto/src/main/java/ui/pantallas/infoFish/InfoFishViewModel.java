package ui.pantallas.infoFish;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Fish;
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

    public void cambioState(Fish fish) {
        state.setValue(new InfoFishState(fish, null));
    }

    public String getFishName(int id) {
        if (svf.getFish(id).isLeft()) {
            state.setValue(new InfoFishState(null, svf.getFish(id).getLeft()));
            return null;
        } else {
            return svf.getFish(id).get().getFile_name();
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

    public Either<String, Fish> llamadaRetrofitAsyncEnUi(int id) {
        if (svf.getFish(id).isLeft()) {
            return Either.left(svf.getFish(id).getLeft());
        } else {
            return Either.right(svf.getFish(id).get());
        }
    }
}
