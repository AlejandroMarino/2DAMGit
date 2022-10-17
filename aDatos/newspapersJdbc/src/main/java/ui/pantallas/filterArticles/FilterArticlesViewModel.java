package ui.pantallas.filterArticles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosArticle;
import servicios.ServiciosArticleType;

public class FilterArticlesViewModel {
    private final ObjectProperty<FilterArticlesState> state;

    private final ServiciosArticle svart;

    public ReadOnlyObjectProperty<FilterArticlesState> getState() {
        return state;
    }

    @Inject
    public FilterArticlesViewModel(ServiciosArticle svart) {
        state = new SimpleObjectProperty<>(new FilterArticlesState(null, null));
        this.svart = svart;
    }


    public void inicio() {
//        if (svart.getAll().isLeft()){
//            state.setValue(new FilterArticlesState(null, svart.getAll().getLeft()));
//        }else {
//            state.setValue(new FilterArticlesState(svart.getAll().get(), null));
//        }
    }

    public void filter(String text) {
//        if (svart.filter(text).isLeft()){
//            state.setValue(new FilterArticlesState(null, null));
//            state.setValue(new FilterArticlesState(null, svart.filter(text).getLeft()));
//        }else {
//            state.setValue(new FilterArticlesState(null, null));
//            state.setValue(new FilterArticlesState(svart.filter(text).get(), null));
//        }
    }
}
