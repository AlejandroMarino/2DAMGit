package servicios;

import data.DaoArticleType;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Type;

import java.util.List;

public class ServicesArticleType {
    private final DaoArticleType daoArticleType;

    @Inject
    public ServicesArticleType(DaoArticleType daoArticleType) {
        this.daoArticleType = daoArticleType;
    }

    public Either<String, List<Type>> getAll(){
        if (daoArticleType.getAll().isLeft()) {
            return Either.left("Error getting types");
        } else {
            return Either.right(daoArticleType.getAll().get());
        }
    }

    public Either<String, Type> getType(int id){
        if (getAll().isLeft()){
            return Either.left(getAll().getLeft());
        }else{
            return Either.right(daoArticleType.get(id).get());
        }
    }

}
