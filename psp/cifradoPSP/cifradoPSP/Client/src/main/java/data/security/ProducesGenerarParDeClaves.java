package data.security;

import asymmetric.GenerarParDeClaves;
import asymmetric.impl.GenerarParDeClavesImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesGenerarParDeClaves {
    @Produces
    public GenerarParDeClaves getGeneradorDeClaves() {
        return new GenerarParDeClavesImpl();
    }
}
