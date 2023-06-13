package security;

import asymmetric.FirmarRandom;
import asymmetric.impl.FirmarRandomImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesSignature {
    @Produces
    public FirmarRandom getFirma() {
        return new FirmarRandomImpl();
    }
}
