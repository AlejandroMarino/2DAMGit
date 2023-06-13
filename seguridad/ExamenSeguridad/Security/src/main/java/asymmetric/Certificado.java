package asymmetric;

import io.vavr.control.Either;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface Certificado {
    Either<String, X509Certificate> certificatePublicKey(byte[] publicKey, PrivateKey serverPK);

    Either<String, X509Certificate> getCertificateFromBase64(String certificate);

    Either<String, String> convertCertificateToBase64(X509Certificate certificate);
}
