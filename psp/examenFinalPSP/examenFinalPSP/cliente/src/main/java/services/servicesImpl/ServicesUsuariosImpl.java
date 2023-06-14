package services.servicesImpl;

import data.dao.DaoUsuarios;
import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesUsuarios;

public class ServicesUsuariosImpl implements ServicesUsuarios {

    private final DaoUsuarios dU;

    @Inject
    public ServicesUsuariosImpl(DaoUsuarios dU) {
        this.dU = dU;
    }

    @Override
    public Single<Either<String, Usuario>> register(Usuario usuario) {
        return dU.register(usuario);
    }

    @Override
    public Single<Either<String, String>> login(Usuario usuario) {
        return dU.login(usuario);
    }
}
