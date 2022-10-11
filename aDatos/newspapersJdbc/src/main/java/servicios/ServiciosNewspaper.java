package servicios;

import data.DaoArticle;
import data.DaoNewspaper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import modelo.Newspaper;

import java.util.List;
import java.util.stream.Collectors;

public class ServiciosNewspaper {
    private final DaoNewspaper daoNewspaper;
    private final DaoArticle daoArticle;
    private final ServiciosArticle serviciosArticle;

    @Inject
    public ServiciosNewspaper(DaoNewspaper daoNewspaper, DaoArticle daoArticle, ServiciosArticle serviciosArticle) {
        this.daoNewspaper = daoNewspaper;
        this.daoArticle = daoArticle;
        this.serviciosArticle = serviciosArticle;
    }

    public boolean delete(Newspaper n){
        if (getAll().isLeft()){
            return false;
        }else {
            List<Article> delArts = daoArticle
                    .getAll()
                    .get()
                    .stream()
                    .filter(article -> article.getIdNewspaper() == n.getId()).collect(Collectors.toList());
            if(serviciosArticle.deleteArts(delArts)) {
                return daoNewspaper.delete(n);
            }else if (delArts.isEmpty()) {
                return daoNewspaper.delete(n);
            }else{
                return false;
            }
        }
    }

    public boolean validoDel(Newspaper n){
        if (getAll().isLeft()||daoArticle.getAll().isLeft()){
            return false;
        }else {
            List<Article> artics = daoArticle.getAll().get();
            int num = (int) artics.stream().filter(article -> article.getIdNewspaper() == n.getId()).count();
            return num == 0;
        }
    }

    public Either<String,Newspaper> get(int id){
        if (daoNewspaper.get(id).isLeft()){
            return Either.left(daoNewspaper.get(id).getLeft());
        }else if (daoNewspaper.get(id).get() == null){
            return Either.left("Id not found");
        }else{
            return Either.right(daoNewspaper.get(id).get());
        }
    }

    public Either<String,List<Newspaper>> getAll(){
        if (daoNewspaper.getAll().isLeft()){
            return Either.left(daoNewspaper.getAll().getLeft());
        }else{
            return Either.right(daoNewspaper.getAll().get());
        }
    }
}
