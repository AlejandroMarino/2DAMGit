package data.dao.daoImpl;

import com.google.gson.Gson;
import common.Constants;
import data.dao.DaoUsuarios;
import data.network.CacheAuthorization;
import data.network.UsuariosApi;
import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Response;

public class DaoUsuariosImpl extends DaoGenerics implements DaoUsuarios {

    private final CacheAuthorization cacheAuthorization;
    private final UsuariosApi usuariosApi;

    @Inject
    public DaoUsuariosImpl(Gson gson, CacheAuthorization cacheAuthorization, UsuariosApi usuariosApi) {
        super(gson);
        this.cacheAuthorization = cacheAuthorization;
        this.usuariosApi = usuariosApi;
    }

    @Override
    public Single<Either<String, Usuario>> register(Usuario usuario) {
        return safeSingleApicall(usuariosApi.register(usuario))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, String>> login(Usuario usuario) {
        Single<Response<Void>> call = usuariosApi.login(usuario);
        return call.map(response -> {
            var retorno = Either.right("Loged").mapLeft(Object::toString);
            if (response.isSuccessful()) {
                String jwt = response.headers().get(Constants.AUTHORIZATION);
                cacheAuthorization.setJwt(jwt);
                cacheAuthorization.setUser(usuario.getNombre());
                cacheAuthorization.setPass(usuario.getPassword());
            } else {
                retorno = Either.left(response.message());
            }
            return retorno;
        }).subscribeOn(Schedulers.io());
    }
}
