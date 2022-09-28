package ui.pantallas.allFish;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.common.BasePantallaController;

public class AllFishController extends BasePantallaController {

    private final AllFishViewModel allFishViewModel;
    @FXML
    private ImageView imgFishing;
    @FXML
    private TableView tableFish;

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
        imgFishing.setImage(new Image(getClass().getResourceAsStream("/images/pesca.png")));
    }

    @Override
    public void principalCargado() {

    }
}
