package ui.pantallas.addArticles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Article;
import servicios.ServiciosArticle;
import servicios.ServiciosArticleType;
import servicios.ServiciosNewspaper;

public class AddArticlesViewModel {
    private final ObjectProperty<AddArticlesState> state;

    private final ServiciosArticle svart;
    private final ServiciosNewspaper svnew;
    private final ServiciosArticleType svarttype;


    public ReadOnlyObjectProperty<AddArticlesState> getState() {
        return state;
    }

    @Inject
    public AddArticlesViewModel(ServiciosArticle svart, ServiciosNewspaper svnew, ServiciosArticleType svarttype) {
        state = new SimpleObjectProperty<>(new AddArticlesState(null, null));
        this.svart = svart;
        this.svnew = svnew;
        this.svarttype = svarttype;
    }

    public void inicio() {
//        if (svart.getAll().isLeft()) {
//            state.setValue(new AddArticlesState(null, svart.getAll().getLeft()));
//        } else {
//            state.setValue(new AddArticlesState(svart.getAll().get(), null));
//        }
    }

    public void add(String id, String name, String description, String typeId, String idNews) {
//        if (svnew.get(Integer.parseInt(idNews)).isLeft()) {
//            state.setValue(new AddArticlesState(null, null));
//            state.setValue(new AddArticlesState(null, "No newspaper found"));
//        } else if (svarttype.getTypeById(Integer.parseInt(typeId)).isLeft()) {
//            state.setValue(new AddArticlesState(null, null));
//            state.setValue(new AddArticlesState(null, svarttype.getTypeById(Integer.parseInt(typeId)).getLeft()));
//        }else {
//            Article art = new Article(Integer.parseInt(id), name, description,Integer.parseInt(typeId),Integer.parseInt(idNews));
//            int n = svart.addArt(art);
//            if (n == -1){
//                state.setValue(new AddArticlesState(null, null));
//                state.setValue(new AddArticlesState(null, "article not added"));
//            }else if (n == 1) {
//                state.setValue(new AddArticlesState(null, null));
//                state.setValue(new AddArticlesState(null, "The Id is not available"));
//            }else {
//                if (svart.getAll().isLeft()) {
//                    state.setValue(new AddArticlesState(null, null));
//                    state.setValue(new AddArticlesState(null, svart.getAll().getLeft()));
//                } else {
//                    state.setValue(new AddArticlesState(null, null));
//                    state.setValue(new AddArticlesState(svart.getAll().get(), null));
//                }
//            }
//        }
    }
}
