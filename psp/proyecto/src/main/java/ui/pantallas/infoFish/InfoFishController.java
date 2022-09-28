package ui.pantallas.infoFish;

import jakarta.inject.Inject;
import ui.common.BasePantallaController;

public class InfoFishController extends BasePantallaController {

    private final InfoFishViewModel infoFishViewModel;

    @Inject
    public InfoFishController(InfoFishViewModel infoFishViewModel) {
        this.infoFishViewModel = infoFishViewModel;
    }

    public void initialize() {
        infoFishViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }
}
