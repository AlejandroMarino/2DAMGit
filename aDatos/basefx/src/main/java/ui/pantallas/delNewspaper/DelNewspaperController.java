package ui.pantallas.delNewspaper;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.common.BasePantallaController;

public class DelNewspaperController extends BasePantallaController {

    private final DelNewspaperViewModel delNewspaperViewModel;

    @Inject
    public DelNewspaperController(DelNewspaperViewModel delNewspaperViewModel) {
        this.delNewspaperViewModel = delNewspaperViewModel;
    }

    public void initialize() {
        delNewspaperViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }

    @FXML
    private void delete(ActionEvent actionEvent) {

    }
}

