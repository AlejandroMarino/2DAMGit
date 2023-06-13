package asymmetric;

import domain.models.Usuario;
import io.vavr.control.Either;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface KeyStore {
    Either<String, String> storeKeys(X509Certificate cert, PrivateKey pk, Usuario user);

    Either<String, PrivateKey> getPrivateKeyFromKeyStore(Usuario user, String passwordStr);

    Either<String, X509Certificate> getCertificateFromKeyStore(Usuario user, String passwordStr);

    Either<String, String> convertPublicKeyToBase64(PublicKey publicKey);

    Either<String, PublicKey> convertBase64ToPublicKey(String keyStr);
}
