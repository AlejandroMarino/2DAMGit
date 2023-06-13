package asymmetric.impl;

import asymmetric.FirmarRandom;
import common.ConstantsErrors;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Firma;

import java.security.*;
import java.util.Base64;

@Log4j2
public class FirmarRandomImpl implements FirmarRandom {

    @Override
    /**
     El texto que hay que firmar se crea en la función (secureRandom)
     */
    public Either<String, Firma> firmarConClavePrivada(PrivateKey key, String texto) {
        Either<String, Firma> result;
        try {
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initSign(key);
            byte[] bytes = texto.getBytes();
            sign.update(bytes);
            String firmaActualizada = Base64.getUrlEncoder().encodeToString(sign.sign());
            Firma firma = new Firma(texto, firmaActualizada);
            result = Either.right(firma);
        } catch (NoSuchAlgorithmException | SignatureException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error signing key");
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Wrong key");
        }
        return result;
    }

    @Override
    public Either<String, String> verificarFirma(Firma firma, PublicKey publicKey) {
        Either<String, String> result;
        try {
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initVerify(publicKey);
            sign.update(firma.getTextoFirmado().getBytes());
            byte[] firmaDescifrada = Base64.getUrlDecoder().decode(firma.getFirmaEnBase64());
            if (sign.verify(firmaDescifrada)) {
                result = Either.right("Login correct");
            } else {
                result = Either.left(ConstantsErrors.INVALID_LOGIN);
            }
        } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error verifying credentials");
        }
        return result;
    }
}
