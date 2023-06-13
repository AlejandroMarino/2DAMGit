package asymmetric;

import io.vavr.control.Either;
import model.Firma;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface FirmarRandom {
    Either<String, Firma> firmarConClavePrivada(PrivateKey key, String texto);

    Either<String, String> verificarFirma(Firma firma, PublicKey key);
}
