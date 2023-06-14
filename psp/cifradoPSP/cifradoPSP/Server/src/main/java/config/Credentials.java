package config;


import asymmetric.KeyStore;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Log4j2
@Singleton
public class Credentials {


    private PublicKey publicKey;
    private PrivateKey privateKey;

    private final KeyStore keyStore;

    @Inject
    public Credentials(KeyStore keys, Configuration config) {
        this.keyStore = keys;
        try (InputStream f = getClass().getResourceAsStream(config.getPath())){
            java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
            ks.load(f, config.getPass().toCharArray());
            keys.getPrivateKeyFromKeyStore(ks, config.getPass()).peek(privateK -> this.privateKey = privateK).peekLeft(log::error);
            keys.getCertificateFromKeyStore(ks).peek(x509Certificate -> this.publicKey = x509Certificate.getPublicKey()).peekLeft(log::error);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }
}
