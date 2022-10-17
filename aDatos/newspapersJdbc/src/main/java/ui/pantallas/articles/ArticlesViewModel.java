package ui.pantallas.articles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosArticle;
import ui.pantallas.filterArticles.FilterArticlesState;

public class ArticlesViewModel {
    private final ObjectProperty<ArticlesState> state;

    private final ServiciosArticle svart;

    public ReadOnlyObjectProperty<ArticlesState> getState() {
        return state;
    }

    @Inject
    public ArticlesViewModel(ServiciosArticle svart) {
        state = new SimpleObjectProperty<>(new ArticlesState(null,null));
        this.svart = svart;
    }

    public void inicio() {
//        if (svart.getAll().isLeft()){
//            state.setValue(new ArticlesState(null, svart.getAll().getLeft()));
//        }else {
//            state.setValue(new ArticlesState(svart.getAll().get(), null));
//        }
    }
}
