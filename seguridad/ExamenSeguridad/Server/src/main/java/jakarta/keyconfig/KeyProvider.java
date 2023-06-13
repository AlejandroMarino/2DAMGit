package jakarta.keyconfig;

import io.jsonwebtoken.security.Keys;
import jakarta.common.Constants;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class KeyProvider {
    private final KeyConfig keyConfig;

    @Inject
    public KeyProvider(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    @Produces
    @Singleton
    @Named(Constants.KEY_PROVIDER_NAME)
    public SecretKey key() {
        SecretKey key;
        try {
            final MessageDigest digest = MessageDigest.getInstance(Constants.ALGORITHM_SHA_512);
            digest.update(keyConfig.getProperty(Constants.PROPERTY_KEY).getBytes(StandardCharsets.UTF_8));
            final SecretKeySpec key2 = new SecretKeySpec(digest.digest(), 0, 64, Constants.ALGORITHM_AES);
            key = Keys.hmacShaKeyFor(key2.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            key = null;
        }
        return key;
    }
}
