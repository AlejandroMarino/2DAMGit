package services.servicesImpl;

import asymmetric.Certificado;
import asymmetric.GenerarParDeClaves;
import asymmetric.KeyStore;
import data.dao.DaoUsuarios;
import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesUsuarios;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;

public class ServicesUsuariosImpl implements ServicesUsuarios {

    private final DaoUsuarios dU;
    private final GenerarParDeClaves generarParDeClaves;
    private final KeyStore keyStore;
    private final Certificado certificado;

    @Inject
    public ServicesUsuariosImpl(DaoUsuarios dU, GenerarParDeClaves generarParDeClaves, KeyStore keyStore, Certificado certificado) {
        this.dU = dU;
        this.generarParDeClaves = generarParDeClaves;
        this.keyStore = keyStore;
        this.certificado = certificado;
    }

    @Override
    public Single<Either<String, Usuario>> register(Usuario usuario) {
        return Single.create(emitter -> {
            Either<String, KeyPair> rGenerar = generarParDeClaves.createKeys();
            if (rGenerar.isLeft()) {
                emitter.onSuccess(Either.left(rGenerar.getLeft()));
            } else {
                Either<String, String> rConvertir = keyStore.convertPublicKeyToBase64(rGenerar.get().getPublic());
                if (rConvertir.isLeft()) {
                    emitter.onSuccess(Either.left(rConvertir.getLeft()));
                } else {
                    String clave = rConvertir.get();
                    usuario.setClave(clave);
                    dU.register(usuario)
                            .observeOn(Schedulers.single())
                            .subscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            emitter.onSuccess(Either.left(either.getLeft()));
                                        } else {
                                            Usuario u = either.get();
                                            Either<String, X509Certificate> rGetCertificado = certificado.getCertificateFromBase64(u.getClave());
                                            if (rGetCertificado.isLeft()) {
                                                emitter.onSuccess(Either.left(rGetCertificado.getLeft()));
                                            } else {
                                                X509Certificate certificado = rGetCertificado.get();
                                                Either<String, String> rStoreKeys = keyStore.storeKeys(certificado, rGenerar.get().getPrivate(), u);
                                                if (rStoreKeys.isLeft()) {
                                                    emitter.onSuccess(Either.left(rStoreKeys.getLeft()));
                                                } else {
                                                    emitter.onSuccess(Either.right(u));
                                                }
                                            }
                                        }
                                    }
                            );
                }
            }
        });
    }

    @Override
    public Single<Either<String, Usuario>> login(Usuario usuario) {
        return dU.login(usuario);
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosOfContrato(int idContrato) {
        return dU.getSicariosOfContrato(idContrato);
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosFilterHabilidad(int habilidad) {
        return dU.getSicariosFilterHabilidad(habilidad);
    }
}
