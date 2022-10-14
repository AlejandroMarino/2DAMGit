package ui.pantallas.allFish;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Fish;
import ui.common.BasePantallaController;

import java.util.List;
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
                tableFish.getItems().clear();
                tableFish.getItems().setAll(newValue.getFishes());
            }

        });
        imgFishing.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constantes.IMAGES_PESCA_PNG))));
    }

    @Override
    public void principalCargado() {
        asyncConTask();
    }

    private void asyncConTask() {

        getPrincipalController().root.setCursor(Cursor.WAIT);
        var task = new Task<Either<String, List<Fish>>>() {
            @Override
            protected Either<String, List<Fish>> call() {
                return allFishViewModel.llamadaRetrofitAsyncEnUi();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
            var result = task.getValue();
            result.peek(fishes -> {
                tableFish.getItems().clear();
                tableFish.getItems().setAll(fishes);

            }).peekLeft(error -> getPrincipalController().error(error));
        });
        task.setOnFailed(workerStateEvent -> {
            getPrincipalController().error(task.getException().getMessage());
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
        });

        new Thread(task).start();
    }

    @FXML
    private void btnDetails() {
        if (tableFish.getSelectionModel().getSelectedItem() != null) {
            getPrincipalController().goInfoFish(allFishViewModel.getFishId(tableFish.getSelectionModel().getSelectedItem()));
        } else {
            getPrincipalController().error(Constantes.SELECT_A_FISH_FROM_THE_TABLE);
        }
    }
}
