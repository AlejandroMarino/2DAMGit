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
        if (daoArticle.addArt(art) == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public boolean availableId(int id){
        if (getAll().isLeft()){
            return false;
        } else {
            return daoArticle.availableId(id);
        }
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