package data.security;

import jakarta.enterprise.inject.Produces;
import symmetric.Encryption;
import symmetric.impl.EncryptionAES;

public class ProducesEncryption {
    @Produces
    public Encryption getEncryption() {
        return new EncryptionAES();
    }

}

