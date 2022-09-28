package ui.pantallas.inicio;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;
import ui.common.BasePantallaController;

import java.util.ResourceBundle;

public class InicioController extends BasePantallaController {

    private final InicioViewModel inicioViewModel;

    @FXML
    private ImageView guitarra;
    @FXML
    private ImageView logo;

    @Inject
    public InicioController(InicioViewModel inicioViewModel) {
        this.inicioViewModel = inicioViewModel;
    }

    public void initialize() {
        inicioViewModel.getState().addListener((observable, oldValue, newValue) -> {
             if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
        logo.setImage(new Image(getClass().getResourceAsStream("/images/Animal_Crossing_Logo.png")));
        guitarra.setImage(new Image(getClass().getResourceAsStream(("/images/guitarrita.png"))));
    }

    @Override
    public void principalCargado() {

    }

    @FXML
    private void contunue(ActionEvent actionEvent) {
        getPrincipalController().goAllFish();
    }
}
