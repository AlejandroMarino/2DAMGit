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

            Path path = Path.of("Server/src/main/resources/data" + user.getNombre() + ".pfx");
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
    public Either<String, PrivateKey> getPrivateKeyFromKeyStore(Usuario user, String passwordStr) {
        Either<String, PrivateKey> result;

        char[] password = passwordStr.toCharArray();
        Path path = Paths.get("Client/data/" + user.getNombre() + ".pfx");
        try (InputStream input = Files.newInputStream(path)) {
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(input, password);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry("private", pt);
            result = Either.right(privateKeyEntry.getPrivateKey());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error getting private key");
        }
        return result;
    }

    @Override
    public Either<String, X509Certificate> getCertificateFromKeyStore(Usuario user, String passwordStr) {
        Either<String, X509Certificate> result;

        char[] password = passwordStr.toCharArray();
        Path path = Paths.get("Client/data/" + user.getNombre() + ".pfx");
        try (InputStream input = Files.newInputStream(path)) {
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(input, password);
            X509Certificate loadedCert = (X509Certificate) ksLoad.getCertificate("public");
            result = Either.right(loadedCert);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error getting certificate");
        }
        return result;
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
}
