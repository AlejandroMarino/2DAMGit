package ui.screens.readers;

import jakarta.inject.Inject;
import ui.common.BasePantallaController;

public class ReadersController extends BasePantallaController {

    private final ReadersViewModel vm;

    @Inject
    public ReadersController(ReadersViewModel vm) {
        this.vm = vm;
    }

    public void initialize() {

        vm.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getReaders() != null) {

            }
        });
    }

    @Override
    public void principalCargado() {
    }
}
