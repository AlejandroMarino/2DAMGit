package ui.pantallas.imagenFish;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.common.BasePantallaController;

public class ImageFishController extends BasePantallaController {

    private final ImageFishViewModel imageFishViewModel;

    @Inject
    public ImageFishController(ImageFishViewModel imageFishViewModel) {
        this.imageFishViewModel = imageFishViewModel;
    }

    @FXML
    private ImageView imgFish;
    @FXML
    private Label labelName;

    public void initialize() {
        imageFishViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getFish() != null) {
                labelName.setText(newValue.getFish().getName().getName_EUen());
                imgFish.setImage(new Image(imageFishViewModel.getFishImage(newValue.getFish().getId())));
            }
        });
    }

    @Override
    public void principalCargado() {
        imageFishViewModel.getFish(getPrincipalController().getActualFish());
    }
}
