package asymmetric;

import io.vavr.control.Either;
import model.User;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface KeyStore {
    Either<String, String> storeKeys(X509Certificate cert, PrivateKey pk, User user);

    Either<String, PrivateKey> getPrivateKeyFromKeyStore(User user, String passwordStr);

    Either<String, X509Certificate> getCertificateFromKeyStore(User user, String passwordStr);

    Either<String, String> convertPublicKeyToBase64(PublicKey publicKey);

    Either<String, PublicKey> convertBase64ToPublicKey(String keyStr);
}
