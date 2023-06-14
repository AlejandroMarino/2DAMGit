package services.servicesImpl;

import asymmetric.Certificado;
import asymmetric.CifrarTextoConClaves;
import asymmetric.Firmar;
import asymmetric.KeyStore;
import com.google.gson.Gson;
import data.dao.DaoContratos;
import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Firma;
import domain.models.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesContratos;
import symmetric.Encryption;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.List;

public class ServicesContratosImpl implements ServicesContratos {

    private final DaoContratos dC;

    private final Firmar firmar;

    private final Gson gson;

    private final KeyStore keyStore;

    private final Certificado certificado;

    private final Encryption encryption;

    private final CifrarTextoConClaves cifrarTextoConClaves;

    @Inject
    public ServicesContratosImpl(DaoContratos dC, Firmar firmar, Gson gson, KeyStore keyStore, Certificado certificado, Encryption encryption, CifrarTextoConClaves cifrarTextoConClaves) {
        this.dC = dC;
        this.firmar = firmar;
        this.gson = gson;
        this.keyStore = keyStore;
        this.certificado = certificado;
        this.encryption = encryption;
        this.cifrarTextoConClaves = cifrarTextoConClaves;
    }

    @Override
    public Single<Either<String, Contrato>> add(Contrato contrato, Detalle detalle, java.security.KeyStore ks) {
        Contrato c = convertirDetalle(detalle, contrato.getUsuario(), ks);
        return dC.add(c);
    }

    @Override
    public Single<Either<String, Contrato>> update(Contrato contrato, Detalle detalle, java.security.KeyStore ks) {
        Contrato c = convertirDetalle(detalle, contrato.getUsuario(), ks);
        return dC.update(c);
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosContratista(int idContratista) {
        return dC.getContratosContratista(idContratista);
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosSicario(int idSicario) {
        return dC.getContratosSicario(idSicario);
    }

    @Override
    public Single<Either<String, List<Contrato>>> getContratosSicarioFilterEstado(int idSicario, Estado estado) {
        return dC.getContratosSicarioFilterEstado(idSicario, estado);
    }

    @Override
    public Single<Either<String, Contrato>> getContrato(int id) {
        return dC.getContrato(id);
    }

    @Override
    public Detalle getDetalleContratoContratista(Contrato contrato, java.security.KeyStore ks) {
        Either<String, PrivateKey> rGetPK = keyStore.getPrivateKeyFromKeyStore(ks, contrato.getUsuario().getPassword());
        if (rGetPK.isLeft()) {
            return null;
        } else {
            Either<String, String> rDescifrarClave = cifrarTextoConClaves.descifrarTextoConClavePrivada(rGetPK.get(), contrato.getClave());
            if (rDescifrarClave.isLeft()) {
                return null;
            } else {
                String detalle = encryption.decrypt(contrato.getDetalle(), rDescifrarClave.get());
                return gson.fromJson(detalle, Detalle.class);
            }
        }
    }

    @Override
    public Detalle getDetalleContratoSicario(String detalle) {
        return gson.fromJson(detalle, Detalle.class);
    }

    private Contrato convertirDetalle(Detalle detalle, Usuario contratista, java.security.KeyStore ks) {
        Contrato c = new Contrato();
        Either<String, PrivateKey> rGetPK = keyStore.getPrivateKeyFromKeyStore(ks, contratista.getPassword());
        if (rGetPK.isLeft()) {
            return null;
        } else {
            String d = gson.toJson(detalle);
            Either<String, Firma> rFirmar = firmar.firmarConClavePrivada(rGetPK.get(), d);
            if (rFirmar.isLeft()) {
                return null;
            } else {
                Firma f = rFirmar.get();

                c.setDetalleFirmado(f.getFirmaEnBase64());
                SecureRandom sr = new SecureRandom();
                byte[] bytes = new byte[16];
                sr.nextBytes(bytes);
                String claveAleatoria = new String(bytes);

                String detalleEncriptado = encryption.encrypt(f.getTextoFirmado(), claveAleatoria);

                c.setDetalle(detalleEncriptado);

                Either<String, PublicKey> rGetPub = certificado.getPublicKeyFromCertificateEncoded(contratista.getClave());
                if (rGetPub.isLeft()) {
                    return null;
                } else {
                    Either<String, String> rCifrar = cifrarTextoConClaves.cifrarTextoConClavePublica(rGetPub.get(), claveAleatoria);
                    if (rCifrar.isLeft()) {
                        return null;
                    } else {
                        c.setClave(rCifrar.get());
                        return c;
                    }
                }
            }
        }
        /*
        firmar con clave privada del contratista
        cifrar con clave aleatoria
        cifrar clave aleatoria con clave publica del contratista
        guardar en base de datos

        en sicarios contratos se mete el contrato cifrado y la clave aleatoria cifrada con la publica del sicario
         */
    }
}
