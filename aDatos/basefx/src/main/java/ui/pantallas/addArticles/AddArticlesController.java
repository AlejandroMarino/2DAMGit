package ui.pantallas.addArticles;

import jakarta.inject.Inject;
import ui.common.BasePantallaController;

public class AddArticlesController extends BasePantallaController {

    private final AddArticlesViewModel addArticlesViewModel;

    @Inject
    public AddArticlesController(AddArticlesViewModel addArticlesViewModel) {
        this.addArticlesViewModel = addArticlesViewModel;
    }

    public void initialize() {
        addArticlesViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
        });
    }

    @Override
    public void principalCargado() {

    }
}
