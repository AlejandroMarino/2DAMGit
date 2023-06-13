package asymmetric;

import domain.models.Firma;
import io.vavr.control.Either;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface Firmar {
    Either<String, Firma> firmarConClavePrivada(PrivateKey key, String texto);

    Either<String, String> verificarFirma(Firma firma, PublicKey key);
}
