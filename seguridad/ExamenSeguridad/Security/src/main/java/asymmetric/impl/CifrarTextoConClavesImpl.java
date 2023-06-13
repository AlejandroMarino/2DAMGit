package asymmetric.impl;

import asymmetric.CifrarTextoConClaves;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Log4j2
public class CifrarTextoConClavesImpl implements CifrarTextoConClaves {
    @Override
    public Either<String, String> cifrarTextoConClavePublica(PublicKey publicKey, String textoParaCifrar) {
        Either<String, String> result;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipheredBytes = cipher.doFinal(textoParaCifrar.getBytes());
            String cipheredText = Base64.getUrlEncoder().encodeToString(cipheredBytes);
            result = Either.right(cipheredText);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error ciphering text");
        }

        return result;
    }

    @Override
    public Either<String, String> descifrarTextoConClavePrivada(PrivateKey privateKey, String textoParaDescifrar) {
        Either<String, String> result;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = Base64.getUrlDecoder().decode(textoParaDescifrar);
            byte[] decipheredBytes = cipher.doFinal(bytes);
            result = Either.right(new String(decipheredBytes, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error deciphering text");
        }

        return result;
    }
}
