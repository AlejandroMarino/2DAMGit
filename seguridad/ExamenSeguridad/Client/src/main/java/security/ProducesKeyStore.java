package security;

import asymmetric.KeyStore;
import asymmetric.impl.KeyStoreImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesKeyStore {
    @Produces
    public KeyStore getKeyStore() {
        return new KeyStoreImpl();
    }
}
