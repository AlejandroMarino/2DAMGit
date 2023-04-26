package services;

import dao.daoSpring.DaoUserSpring;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServicesUser {

    private DaoUserSpring dU;

    @Inject
    public ServicesUser(DaoUserSpring dU) {
        this.dU = dU;
    }

    public Either<String, Void> updateUserName(Usuario u) {
        u.setId(2);
        Either<Integer, Void> r = dU.update(u);
        if (r.isLeft()){
            return Either.left("Error while updating username");
        } else {
            return Either.right(null);
        }
    }
}
