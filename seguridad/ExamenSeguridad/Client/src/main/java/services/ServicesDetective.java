package services;

import asymmetric.CifrarTextoConClaves;
import asymmetric.FirmarRandom;
import asymmetric.KeyStore;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Firma;
import model.User;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

@Log4j2
public class ServicesDetective {
    private final KeyStore keyStore;
    private final CifrarTextoConClaves cif;
    private final FirmarRandom firmarRandom;

    @Inject
    public ServicesDetective(KeyStore keyStore, CifrarTextoConClaves cif, FirmarRandom firmarRandom) {
        this.keyStore = keyStore;
        this.cif = cif;
        this.firmarRandom = firmarRandom;
    }

    public Either<String, PrivateKey> getDetectivePrivateKey(User user, String password) {
        Either<String, PrivateKey> result;
        Either<String, PrivateKey> pk = keyStore.getPrivateKeyFromKeyStore(user, password);
        if (pk.isRight()) {
            result = Either.right(pk.get());
        } else {
            result = Either.left("Error retrieving private key");
        }

        return result;
    }

    public Either<String, PublicKey> getDetectivePublicKey(User user, String password) {
        Either<String, PublicKey> result;
        Either<String, X509Certificate> cert = keyStore.getCertificateFromKeyStore(user, password);
        if (cert.isRight()) {
            result = Either.right(cert.get().getPublicKey());
        } else {
            result = Either.left("Error retrieving public key");
        }

        return result;
    }

    public Either<String, String> cifrarTexto(String texto, PublicKey publicKey) {
        return cif.cifrarTextoConClavePublica(publicKey, texto);
    }

    public Either<String, String> descifrarTexto(String texto, PrivateKey privateKey) {
        return cif.descifrarTextoConClavePrivada(privateKey, texto);
    }

    public Either<String, Firma> firmarInforme(PrivateKey privateKey, String informe) {
        return firmarRandom.firmarConClavePrivada(privateKey, informe);
    }

    public Either<String, String> verificarFirma(Firma firma, PublicKey publicKey) {
        return firmarRandom.verificarFirma(firma, publicKey);
    }
}
