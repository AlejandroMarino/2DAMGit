package servicios;

import data.DaoArticle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;

import java.util.List;

public class ServiciosArticle {
    private final DaoArticle daoArticle;
    private final ServiciosArticleType serviciosArticleType;

    @Inject
    public ServiciosArticle(DaoArticle daoArticle, ServiciosArticleType serviciosArticleType) {
        this.daoArticle = daoArticle;
        this.serviciosArticleType = serviciosArticleType;
    }

    public boolean deleteArts(List<Article> arts) {
        if (arts.isEmpty()) {
            return false;
        } else {
            arts.forEach(daoArticle::delete);
            return true;
        }
    }

    public int addArt(Article art) {
        return daoArticle.addArt(art);
    }

    public Either<String,List<Article>> getAll(){
        return daoArticle.getAll();
    }

    public Either<String,List<Article>> filter(String typeName){
        if (serviciosArticleType.getType(typeName).isLeft()){
            return Either.left(serviciosArticleType.getType(typeName).getLeft());
        }else {
            return daoArticle.filter(serviciosArticleType.getType(typeName).get());
        }
    }
}