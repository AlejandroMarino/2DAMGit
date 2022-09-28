package ui.pantallas.allFish;

import jakarta.inject.Inject;
import ui.common.BasePantallaController;

public class AllFishController extends BasePantallaController {

    private final AllFishViewModel allFishViewModel;

    @Inject
    public AllFishController(AllFishViewModel allFishViewModel) {
        this.allFishViewModel = allFishViewModel;
    }

    public void initialize() {
        allFishViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }
}
