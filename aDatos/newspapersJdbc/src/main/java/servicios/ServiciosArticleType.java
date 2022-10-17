package servicios;

import data.DaoArticleType;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Type;

import java.util.List;

public class ServiciosArticleType {
    private final DaoArticleType daoArticleType;

    @Inject
    public ServiciosArticleType(DaoArticleType daoArticleType) {
        this.daoArticleType = daoArticleType;
    }

//    public Either<String, List<Type>> getAll(){
//        return daoArticleType.allTypes();
//    }

//    public Either<String, Type> getType(String name){
//        if (getAll().isLeft()){
//            return Either.left(getAll().getLeft());
//        }else{
//            return daoArticleType.getType(name);
//        }
//    }

//    public Either<String, Type> getTypeById(int id) {
//        if (getAll().isLeft()){
//            return Either.left(getAll().getLeft());
//        }else{
//            return daoArticleType.getTypeId(id);
//        }
//    }
}
