package ui.detectives;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Firma;
import model.User;
import services.ServicesDetective;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ExerciseDetective {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesDetective services = container.select(ServicesDetective.class).get();
        ExerciseDetective example = new ExerciseDetective();

        Either<String, Firma> firma = example.firmarInforme(services);
        Either<String, String> textoCifrado = Either.left("Error al cifrar");
        if (firma.isRight()) {
            textoCifrado = example.cifrarInforme(services, firma.get());
        }
        if (textoCifrado.isRight()) {
            example.comprobarFirmaYDescifrarInforme(services, textoCifrado.get(), firma.get());
        } else {
            System.out.println(textoCifrado.getLeft());
        }
    }

    public Either<String, Firma> firmarInforme(ServicesDetective services) {
        String informe = "informe PRUEBA COSAS";

        Either<String, PrivateKey> privateKeyDetective1 = services.getDetectivePrivateKey(new User("1"), "1");
        Either<String, Firma> firma = Either.left("Error");
        if (privateKeyDetective1.isRight()) {
            firma = services.firmarInforme(privateKeyDetective1.get(), informe);
        }
        return firma;
    }

    public Either<String, String> cifrarInforme(ServicesDetective services, Firma firma) {
        Either<String, PublicKey> publicKeyDetective2 = services.getDetectivePublicKey(new User("2"), "2");

        Either<String, String> textoDescifrado = Either.left("Error al descifrar el texto");
        if (publicKeyDetective2.isRight()) {
            textoDescifrado = services.cifrarTexto(firma.getTextoFirmado(), publicKeyDetective2.get());
        }

        return textoDescifrado;
    }

    public void comprobarFirmaYDescifrarInforme(ServicesDetective services, String textoCifrado, Firma firma) {
        Either<String, PrivateKey> privateKeyDetective2 = services.getDetectivePrivateKey(new User("2"), "2");

        Either<String, String> textoDescifrado = Either.left("Error al descifrar el texto");
        if (privateKeyDetective2.isRight()) {
            textoDescifrado = services.descifrarTexto(textoCifrado, privateKeyDetective2.get());
        }
        Either<String, String> resultado = Either.left("Firma no verificada");
        if (textoDescifrado.isRight()) {
            Either<String, PublicKey> publicKeyDetective1 = services.getDetectivePublicKey(new User("1"), "1");
            if (publicKeyDetective1.isRight()) {
                resultado = services.verificarFirma(firma, publicKeyDetective1.get());
            }
        }

        System.out.println("Informe: " + textoDescifrado.get());
        if (resultado.isRight()) {
            System.out.println("Firma del detective 1 verificada");
        } else {
            System.out.println(resultado.getLeft());
        }
    }


}