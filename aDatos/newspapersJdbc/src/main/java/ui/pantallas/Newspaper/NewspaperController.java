package ui.pantallas.Newspaper;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
import ui.common.BasePantallaController;

public class NewspaperController extends BasePantallaController {

    @FXML
    private TableView<Newspaper> tableNews;
    @FXML
    private TableColumn<String, Newspaper> tcId;
    @FXML
    private TableColumn<String, Newspaper> tcName;
    @FXML
    private TableColumn<String, Newspaper> tcPrice;
    @FXML
    private TableColumn<String, Newspaper> tcDirector;


    private final NewspaperViewModel newspaperViewModel;

    @Inject
    public NewspaperController(NewspaperViewModel newspaperViewModel) {
        this.newspaperViewModel = newspaperViewModel;
    }

    public void initialize() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        newspaperViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            } else if (newValue.getNewspapers() != null) {
                tableNews.getItems().clear();
                tableNews.getItems().addAll(newValue.getNewspapers());
            }
        });
    }

    @Override
    public void principalCargado() {
        newspaperViewModel.inicio();
    }
}
