package ui.pantallas.delNewspaper;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
import ui.common.BasePantallaController;

public class DelNewspaperController extends BasePantallaController {

    private final DelNewspaperViewModel delNewspaperViewModel;
    @FXML
    private TableView<Newspaper> tableNewspaper;
    @FXML
    private TableColumn<String,Newspaper> tcId;
    @FXML
    private TableColumn<String,Newspaper> tcName;
    @FXML
    private TableColumn<String,Newspaper> tcPrice;
    @FXML
    private TableColumn<String,Newspaper> tcDirector;

    @Inject
    public DelNewspaperController(DelNewspaperViewModel delNewspaperViewModel) {
        this.delNewspaperViewModel = delNewspaperViewModel;
    }

    public void initialize() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        delNewspaperViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }else if (newValue.getNewspapers() != null) {
                tableNewspaper.getItems().clear();
                tableNewspaper.getItems().addAll(newValue.getNewspapers());
            }
        });
    }

    @Override
    public void principalCargado() {
        delNewspaperViewModel.inicio();
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
        if (tableNewspaper.getSelectionModel().getSelectedItem() != null) {
            delNewspaperViewModel.delete(tableNewspaper.getSelectionModel().getSelectedItem());
        } else {
            getPrincipalController().error("Select a newspaper from the table");
        }
    }
}

