package ui.pantallas.Newspaper;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ui.common.BasePantallaController;

public class NewspaperController extends BasePantallaController {

    @FXML
    private ListView listNewspaper;

    private final NewspaperViewModel newspaperViewModel;

    @Inject
    public NewspaperController(NewspaperViewModel newspaperViewModel) {
        this.newspaperViewModel = newspaperViewModel;
    }

    public void initialize() {
        newspaperViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }
}
