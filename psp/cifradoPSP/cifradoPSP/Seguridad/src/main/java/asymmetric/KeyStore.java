package asymmetric;

import domain.models.Usuario;
import io.vavr.control.Either;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface KeyStore {
    Either<String, String> storeKeys(X509Certificate cert, PrivateKey pk, Usuario user);

    Either<String, PrivateKey> getPrivateKeyFromKeyStore(java.security.KeyStore keyStore, String password);

    Either<String, X509Certificate> getCertificateFromKeyStore(java.security.KeyStore keyStore);

    Either<String, String> convertPublicKeyToBase64(PublicKey publicKey);

    Either<String, PublicKey> convertBase64ToPublicKey(String keyStr);

    Either<String, java.security.KeyStore> getKeyStore(String pathKeyStore, String password);
}
