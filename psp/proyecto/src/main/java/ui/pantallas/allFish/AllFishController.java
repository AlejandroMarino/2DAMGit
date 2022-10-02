package ui.pantallas.allFish;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Fish;
import ui.common.BasePantallaController;

import java.util.Objects;

public class AllFishController extends BasePantallaController {

    private final AllFishViewModel allFishViewModel;
    @FXML
    private ImageView imgFishing;
    @FXML
    private TableView<Fish> tableFish;
    @FXML
    private TableColumn<String, Fish> tCName;
    @FXML
    private TableColumn<String, Fish> tCShadow;

    @Inject
    public AllFishController(AllFishViewModel allFishViewModel) {
        this.allFishViewModel = allFishViewModel;
    }

    public void initialize() {
        tCName.setCellValueFactory(new PropertyValueFactory<>("file_name"));
        tCShadow.setCellValueFactory(new PropertyValueFactory<>("shadow"));

        allFishViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getFishes() != null) {
                tableFish.getItems().setAll(newValue.getFishes());
            }

        });
        imgFishing.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pesca.png"))));
    }

    @Override
    public void principalCargado() {
        allFishViewModel.inicio();
    }

    @FXML
    private void btnDetails() {
        if (tableFish.getSelectionModel().getSelectedItem() != null) {
            getPrincipalController().goInfoFish(allFishViewModel.getFishId(tableFish.getSelectionModel().getSelectedItem()));
        } else {
            getPrincipalController().error("Select a fish from the table");
        }
    }
}
