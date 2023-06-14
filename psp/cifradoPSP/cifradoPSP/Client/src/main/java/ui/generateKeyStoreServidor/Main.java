package ui.generateKeyStoreServidor;

import asymmetric.Certificado;
import asymmetric.GenerarParDeClaves;
import asymmetric.KeyStore;
import domain.models.Usuario;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        GenerarParDeClaves generarParDeClaves = container.select(GenerarParDeClaves.class).get();
        Certificado certificado = container.select(Certificado.class).get();
        KeyStore keyStore = container.select(KeyStore.class).get();

        Either<String, KeyPair> keys = generarParDeClaves.createKeys();
        if (keys.isRight()) {
            KeyPair keyPair = keys.get();
            Either<String, X509Certificate> certificar = certificado.certificatePublicKey(keyPair.getPublic().getEncoded(), keyPair.getPrivate());
            if (certificar.isRight()) {
                X509Certificate certificate = certificar.get();
                keyStore.storeKeys(certificate, keyPair.getPrivate(), new Usuario("server", "server"));
            } else {
                System.out.println("Error: " + certificar.getLeft());
            }
        } else {
            System.out.println("Error: " + keys.getLeft());
        }
    }
}