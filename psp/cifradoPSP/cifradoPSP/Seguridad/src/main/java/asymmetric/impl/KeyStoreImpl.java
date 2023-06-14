package asymmetric.impl;

import domain.models.Usuario;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Log4j2
public class KeyStoreImpl implements asymmetric.KeyStore {

    @Override
    public Either<String, String> storeKeys(X509Certificate cert, PrivateKey pk, Usuario user) {
        Either<String, String> result;
        KeyStore ks;
        try {
            // Guardar clave privada y certificado de la pública del usuario
            // Utilizar contraseña de registro del usuario para proteger el archivo
            ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            ks.setCertificateEntry("public", cert);
            char[] password = user.getPassword().toCharArray();
            ks.setKeyEntry("private", pk, password, new Certificate[]{cert});

            Path path = Path.of("Client/data/" + user.getNombre() + ".pfx");
            File keyStore = new File(path.toUri());
            if (keyStore.createNewFile()) {
                OutputStream stream = new FileOutputStream(path.toFile());
                ks.store(stream, password);
                result = Either.right("Keys successfully stored");
            } else {
                result = Either.left("Error creating file");
            }
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Unable to store keys");
        }

        return result;
    }

    @Override
    public Either<String, PrivateKey> getPrivateKeyFromKeyStore(KeyStore keyStore, String password) {
        try {
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("private", pt);
            return Either.right(privateKeyEntry.getPrivateKey());
        } catch (UnrecoverableEntryException | KeyStoreException | NoSuchAlgorithmException e) {
            log.error("error al encontrar la clave ", e);
            return Either.left("error al encontrar la clave");
        }
    }

    @Override
    public Either<String, X509Certificate> getCertificateFromKeyStore(KeyStore keyStore) {
        try {
            return Either.right((X509Certificate) keyStore.getCertificate("public"));
        } catch (KeyStoreException e) {
            log.error("error al obtener el certificado", e);
            return Either.left("error al obtener el certificado");
        }
    }

    @Override
    public Either<String, String> convertPublicKeyToBase64(PublicKey publicKey) {
        Either<String, String> result;
        String cert = Base64.getUrlEncoder().encodeToString(publicKey.getEncoded());
        if (cert != null) {
            result = Either.right(cert);
        } else {
            result = Either.left("Error encoding public key");
        }
        return result;
    }

    @Override
    public Either<String, PublicKey> convertBase64ToPublicKey(String keyStr) {
        Either<String, PublicKey> result;
        try {
            byte[] publicKeyBytes = Base64.getUrlDecoder().decode(keyStr);
            X509EncodedKeySpec publicKey = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            result = Either.right(kf.generatePublic(publicKey));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error decoding public key");
        }
        return result;
    }

    @Override
    public Either<String, KeyStore> getKeyStore(String pathKeyStore, String password) {
        KeyStore keyStore;
        try (FileInputStream fileInputStream = new FileInputStream(pathKeyStore)) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileInputStream, password.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error("error al encontrar el keystore", e);
            log.info(Paths.get(pathKeyStore).toAbsolutePath().toString());
            return Either.left("Error al encontrar el keystore");
        }
        return Either.right(keyStore);
    }
}
