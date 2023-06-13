package asymmetric;

import io.vavr.control.Either;

import java.security.KeyPair;

public interface GenerarParDeClaves {
    Either<String, KeyPair> createKeys(String user);
}
