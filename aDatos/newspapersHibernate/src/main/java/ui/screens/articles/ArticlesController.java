package ui.screens.articles;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Article;
import ui.common.BaseScreenController;

public class ArticlesController extends BaseScreenController {

    private final ArticlesViewModel articlesViewModel;
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
    public ArticlesController(ArticlesViewModel articlesViewModel) {
        this.articlesViewModel = articlesViewModel;
    }

    public void initialize() {
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columIdNewspaper.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        articlesViewModel.getState().addListener((observable, oldValue, newValue) -> {
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
        articlesViewModel.inicio();
    }
}
