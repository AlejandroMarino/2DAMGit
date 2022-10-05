package ui.pantallas.inicio;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.common.BasePantallaController;

import java.util.Objects;

public class InicioController extends BasePantallaController {

    private final InicioViewModel inicioViewModel;

    @FXML
    private ImageView btnGuitarra;
    @FXML
    private ImageView imgLogo;

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
        imgLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Animal_Crossing_Logo.png"))));
        btnGuitarra.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(("/images/guitarrita.png")))));

    }

    @FXML
    private void contunue() {
        getPrincipalController().goAllFish();
    }
}
