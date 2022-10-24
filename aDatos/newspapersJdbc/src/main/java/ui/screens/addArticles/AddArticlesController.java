package ui.screens.addArticles;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Article;
import ui.common.BaseScreenController;

public class AddArticlesController extends BaseScreenController {

    private final AddArticlesViewModel addArticlesViewModel;
    @FXML
    private TextField txId;
    @FXML
    private TextField txName;
    @FXML
    private TextField txDescription;
    @FXML
    private TextField txType;
    @FXML
    private TextField txNewspaper;
    @FXML
    private TableView<Article> tableArticle;
    @FXML
    private TableColumn<String,Article> columId;
    @FXML
    private TableColumn<String,Article> columName;
    @FXML
    private TableColumn<String,Article> columDescription;
    @FXML
    private TableColumn<String,Article> columType;
    @FXML
    private TableColumn<String,Article> columIdNewspaper;

    @Inject
    public AddArticlesController(AddArticlesViewModel addArticlesViewModel) {
        this.addArticlesViewModel = addArticlesViewModel;
    }

    public void initialize() {
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columIdNewspaper.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));

        addArticlesViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getArticles() != null) {
                tableArticle.getItems().clear();
                tableArticle.getItems().setAll(newValue.getArticles());
            }
        });
    }

    @Override
    public void principalCargado() {
        addArticlesViewModel.inicio();
    }

    @FXML
    private void add(ActionEvent actionEvent) {
        if (txId.getText().isEmpty() || txName.getText().isEmpty() || txDescription.getText().isEmpty() || txType.getText().isEmpty() || txNewspaper.getText().isEmpty()) {
            getPrincipalController().error("Wright all the fields");
        } else {
            addArticlesViewModel.add(txId.getText(), txName.getText(), txDescription.getText(), txType.getText(), txNewspaper.getText());
        }
    }
}
