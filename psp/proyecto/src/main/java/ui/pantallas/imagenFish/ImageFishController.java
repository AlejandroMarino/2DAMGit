package ui.pantallas.imagenFish;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
        imageFishViewModel.getState().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getFish() != null) {
                labelName.setText(newValue.getFish().getName().getName_EUen());
                imgFish.setImage(new Image(newValue.getFish().getImage_uri()));
            }
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
        }));
    }

    @Override
    public void principalCargado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        imageFishViewModel.getFish(getPrincipalController().getActualFishName());
    }
}
