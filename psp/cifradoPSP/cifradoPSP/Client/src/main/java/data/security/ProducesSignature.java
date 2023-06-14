package data.security;

import asymmetric.Firmar;
import asymmetric.impl.FirmarImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesSignature {
    @Produces
    public Firmar getFirma() {
        return new FirmarImpl();
    }
}
