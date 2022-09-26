package ui.pantallas.articles;

import jakarta.inject.Inject;
import ui.common.BasePantallaController;

public class ArticlesController extends BasePantallaController {

    private final ArticlesViewModel articlesViewModel;

    @Inject
    public ArticlesController(ArticlesViewModel articlesViewModel) {
        this.articlesViewModel = articlesViewModel;
    }

    public void initialize() {
        articlesViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }
}
