package security;

import asymmetric.Certificado;
import asymmetric.impl.CertificadoImpl;
import jakarta.enterprise.inject.Produces;

public class ProducesCertificado {
    @Produces
    public Certificado getCertificate() {
        return new CertificadoImpl();
    }
}
