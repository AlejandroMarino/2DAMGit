package servicios;

import data.DaoArticle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import modelo.Type;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesArticle {
    private final DaoArticle daoArticle;
    private final ServicesArticleType servicesArticleType;

    @Inject
    public ServicesArticle(DaoArticle daoArticle, ServicesArticleType servicesArticleType) {
        this.daoArticle = daoArticle;
        this.servicesArticleType = servicesArticleType;
    }

    public boolean deleteArts(List<Article> arts) {
        if (arts.isEmpty()) {
            return false;
        } else {
            arts.forEach(art -> daoArticle.delete(art.getId()));
            return true;
        }
    }

    public int addArt(Article art) {
        return daoArticle.add(art);
    }

    public Either<String,List<Article>> getAll(){
        if (daoArticle.getAll().isLeft()) {
            return Either.left("Error getting articles");
        } else {
            return Either.right(daoArticle.getAll().get());
        }
    }

    public Either<String,List<Article>> filter(int idType){
        if (servicesArticleType.getType(idType).isLeft()){
            return Either.left(servicesArticleType.getType(idType).getLeft());
        }else {
            if (getAll().isLeft()){
                return Either.left(getAll().getLeft());
            }else {
                Type type = servicesArticleType.getType(idType).get();
                List<Article> articles = getAll()
                        .get()
                        .stream()
                        .filter(article -> article.getType() == type.getId()).collect(Collectors.toUnmodifiableList());
                return Either.right(articles);
            }
        }
    }
}