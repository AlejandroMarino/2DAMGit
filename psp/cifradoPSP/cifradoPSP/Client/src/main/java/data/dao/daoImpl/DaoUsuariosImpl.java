package data.dao.daoImpl;

import com.google.gson.Gson;
import data.dao.DaoUsuarios;
import data.network.UsuarioApi;
import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoUsuariosImpl extends DaoGenerics implements DaoUsuarios {

    private final UsuarioApi usuarioApi;

    @Inject
    public DaoUsuariosImpl(Gson gson, UsuarioApi usuarioApi) {
        super(gson);
        this.usuarioApi = usuarioApi;
    }


    @Override
    public Single<Either<String, Usuario>> register(Usuario usuario) {
        return safeSingleApicall(usuarioApi.register(usuario))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Usuario>> login(Usuario usuario) {
        return safeSingleApicall(usuarioApi.login(usuario))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosOfContrato(int idContrato) {
        return safeSingleApicall(usuarioApi.getSicariosContrato(idContrato))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosFilterHabilidad(int habilidad) {
        return safeSingleApicall(usuarioApi.getSicariosFilterHabilidad(habilidad))
                .subscribeOn(Schedulers.io());
    }
}
