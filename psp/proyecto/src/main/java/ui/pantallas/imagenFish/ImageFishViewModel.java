package ui.pantallas.imagenFish;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosFish;

public class
ImageFishViewModel {

    private final ServiciosFish svf;
    private final ObjectProperty<ImageFishState> state;

    public ReadOnlyObjectProperty<ImageFishState> getState() {
        return state;
    }

    @Inject
    public ImageFishViewModel(ServiciosFish svf) {
        state = new SimpleObjectProperty<>(new ImageFishState(null, null));
        this.svf = svf;
    }

    public void getFish(String name) {
        svf.llamadaRetrofitSingle(name)
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft()) {
                        state.setValue(new ImageFishState(null, either.getLeft()));
                    } else {
                        state.setValue(new ImageFishState(either.get(), null));
                    }
                });
    }
}
