package servicios;

import data.DaoArticle;
import jakarta.inject.Inject;

public class ServicesArticle {
    private final DaoArticle daoArticle;
    private final ServicesArticleType servicesArticleType;

    @Inject
    public ServicesArticle(DaoArticle daoArticle, ServicesArticleType servicesArticleType) {
        this.daoArticle = daoArticle;
        this.servicesArticleType = servicesArticleType;
    }

//    public boolean deleteArts(List<Article> arts) {
//        if (arts.isEmpty()) {
//            return false;
//        } else {
//            arts.forEach(daoArticle::delete);
//            return true;
//        }
//    }

//    public int addArt(Article art) {
//        return daoArticle.addArt(art);
//    }

//    public Either<String,List<Article>> getAll(){
//        return daoArticle.getAll();
//    }

//    public Either<String,List<Article>> filter(String typeName){
//        if (serviciosArticleType.getType(typeName).isLeft()){
//            return Either.left(serviciosArticleType.getType(typeName).getLeft());
//        }else {
//            if (getAll().isLeft()){
//                return Either.left(getAll().getLeft());
//            }else {
//                Type type = serviciosArticleType.getType(typeName).get();
//                List<Article> articles = getAll()
//                        .get()
//                        .stream()
//                        .filter(article -> article.getType() == type.getId()).collect(Collectors.toUnmodifiableList());
//                return Either.right(articles);
//            }
//        }
//    }
}