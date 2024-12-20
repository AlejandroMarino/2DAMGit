package domain.services.servicesImpl;

import asymmetric.Certificado;
import asymmetric.KeyStore;
import config.Credentials;
import data.dao.DaoUsuario;
import data.model.UsuarioEntity;
import data.model.mapper.UsuariosMapper;
import domain.models.Usuario;
import domain.services.ServicesUsuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

public class ServicesUsuarioImpl implements ServicesUsuario {

    private final DaoUsuario dU;

    private final UsuariosMapper uM;

    private final Pbkdf2PasswordHash passwordHash;

    private final KeyStore keyStore;

    private final Certificado certificado;

    private final Credentials credentials;

    @Inject
    public ServicesUsuarioImpl(DaoUsuario dU, UsuariosMapper uM, Pbkdf2PasswordHash passwordHash, KeyStore keyStore, Certificado certificado, Credentials credentials) {
        this.dU = dU;
        this.uM = uM;
        this.passwordHash = passwordHash;
        this.keyStore = keyStore;
        this.certificado = certificado;
        this.credentials = credentials;
    }

    @Override
    public Usuario register(Usuario usuario) {

        try {
            PrivateKey privateKey = credentials.getPrivateKey();

            Either<String, PublicKey> rGetPub = keyStore.convertBase64ToPublicKey(usuario.getClave());
            if (rGetPub.isLeft()) {
                return null;
            } else {
                Either<String, X509Certificate> rGetCertificado = certificado.certificatePublicKey(rGetPub.get().getEncoded(), privateKey);
                if (rGetCertificado.isLeft()) {
                    return null;
                } else {
                    Either<String, String> rConvertCertificado = certificado.convertCertificateToBase64(rGetCertificado.get());
                    if (rConvertCertificado.isLeft()) {
                        return null;
                    } else {
                        usuario.setClave(rConvertCertificado.get());
                        UsuarioEntity uE = dU.add(uM.toUsuarioEntity(usuario));
                        return uM.toUsuario(uE);
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuario login(Usuario usuario) {
        char[] pass = usuario.getPassword().toCharArray();
        UsuarioEntity userE = dU.get(usuario.getNombre());
        Usuario user = uM.toUsuario(userE);
        if (user == null) {
            return null;
        } else {
            if (passwordHash.verify(pass, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Usuario> getSicariosFilterHabilidad(int habilidad) {
        List<UsuarioEntity> uE = dU.getSicariosFilterHabilidad(habilidad);
        return uE.stream().map(uM::toUsuario).toList();
    }

    @Override
    public List<Usuario> getSicariosContrato(int idContrato) {
        List<UsuarioEntity> uE = dU.getSicarios(idContrato);
        return uE.stream().map(uM::toUsuario).toList();
    }


}
