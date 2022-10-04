package servicios;

import data.DaoArticle;
import jakarta.inject.Inject;
import modelo.Article;

import java.util.List;

public class ServiciosArticle {
    private final DaoArticle daoArticle;

    @Inject
    public ServiciosArticle(DaoArticle daoArticle) {
        this.daoArticle = daoArticle;
    }

    public boolean deleteArts(List<Article> arts) {
        if (arts.isEmpty()) {
            return false;
        } else {
            arts.forEach(daoArticle::delete);
            return true;
        }
    }
}