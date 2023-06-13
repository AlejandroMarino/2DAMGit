package security.cachekeys;

import jakarta.inject.Singleton;
import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Data
@Singleton
public class CacheKeys {
    private String userName;
    private PrivateKey userPrivateKey;
    private X509Certificate userCertificate;
    private String userPFXPassword;
    private PublicKey serverPublicKey;
}
