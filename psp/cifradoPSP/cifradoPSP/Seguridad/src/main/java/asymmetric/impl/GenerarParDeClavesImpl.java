package asymmetric.impl;

import asymmetric.GenerarParDeClaves;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Log4j2
public class GenerarParDeClavesImpl implements GenerarParDeClaves {

    @Override
    public Either<String, KeyPair> createKeys() {
        Either<String, KeyPair> result;
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048, new SecureRandom());
            KeyPair keyPair = keyGenerator.generateKeyPair();
            result = Either.right(keyPair);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error creating keys");
        }

        return result;
    }
}
