package asymmetric;

import io.vavr.control.Either;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface CifrarTextoConClaves {
    Either<String, String> cifrarTextoConClavePublica(PublicKey publicKey, String textoParaCifrar);

    Either<String, String> descifrarTextoConClavePrivada(PrivateKey privateKey, String textoParaDescifrar);
}
