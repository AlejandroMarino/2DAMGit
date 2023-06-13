package security;

import asymmetric.CifrarTextoConClaves;
import asymmetric.impl.CifrarTextoConClavesImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesCifrar {
    @Produces
    public CifrarTextoConClaves getCifrado() {
        return new CifrarTextoConClavesImpl();
    }
}
