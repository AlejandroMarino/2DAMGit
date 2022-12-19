package servicios;

import data.DaoArticle;
import data.DaoNewspaper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import modelo.Newspaper;

import java.util.List;

public class ServicesNewspaper {
    private final DaoNewspaper daoNewspaper;
    private final DaoArticle daoArticle;
    private final ServicesArticle servicesArticle;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper, DaoArticle daoArticle, ServicesArticle servicesArticle) {
        this.daoNewspaper = daoNewspaper;
        this.daoArticle = daoArticle;
        this.servicesArticle = servicesArticle;
    }

    public boolean delete(Newspaper n) {
        if (getAll().isLeft()) {
            return false;
        } else {
            return daoNewspaper.delete(n.getId()) > 0;
        }
    }

    public boolean validoDel(Newspaper n) {
        if (getAll().isLeft() || daoArticle.getAll().isLeft()) {
            return false;
        } else {
            List<Article> artics = daoArticle.getAll().get();
            int num = (int) artics.stream().filter(article -> article.getIdNewspaper() == n.getId()).count();
            return num == 0;
        }
    }

    public Either<String, Newspaper> get(int id) {
        if (daoNewspaper.get(id).isLeft()) {
            return Either.left("Error getting newspaper");
        } else if (daoNewspaper.get(id).get() == null) {
            return Either.left("Id not found");
        } else {
            return Either.right(daoNewspaper.get(id).get());
        }
    }

    public Either<String, List<Newspaper>> getAll() {
        if (daoNewspaper.getAll().isLeft()) {
            return Either.left("Error getting newspaper");
        } else {
            return Either.right(daoNewspaper.getAll().get());
        }
    }
}
