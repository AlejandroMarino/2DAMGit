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

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

public class ServicesContratosImpl implements ServicesContratos {

    private final DaoContratos dC;

    private final Firmar firmar;

    private final Gson gson;

    private final KeyStore keyStore;

    private final Certificado certificado;

    private final CifrarTextoConClaves cifrarTextoConClaves;

    @Inject
    public ServicesContratosImpl(DaoContratos dC, Firmar firmar, Gson gson, KeyStore keyStore, Certificado certificado, CifrarTextoConClaves cifrarTextoConClaves) {
        this.dC = dC;
        this.firmar = firmar;
        this.gson = gson;
        this.keyStore = keyStore;
        this.certificado = certificado;
        this.cifrarTextoConClaves = cifrarTextoConClaves;
    }

    @Override
    public Single<Either<String, Contrato>> add(Contrato contrato, Detalle detalle) {
        Contrato c = convertirDetalle(detalle, contrato.getUsuario());
        return dC.add(c);
    }

    @Override
    public Single<Either<String, Contrato>> update(Contrato contrato, Detalle detalle) {
        Contrato c = convertirDetalle(detalle, contrato.getUsuario());
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
    public Detalle getDetalleContrato(String detalle) {
        return gson.fromJson(detalle, Detalle.class);
    }

    private Contrato convertirDetalle(Detalle detalle, Usuario contratista) {
        Contrato c = new Contrato();
        Either<String, PrivateKey> rGetPK = keyStore.getPrivateKeyFromKeyStore(contratista, contratista.getPassword());
        if (rGetPK.isLeft()) {
            return null;
        } else {
            String d = gson.toJson(detalle);
            Either<String, Firma> rFirmar = firmar.firmarConClavePrivada(rGetPK.get(), d);
            if (rFirmar.isLeft()) {
                return null;
            } else {
                Firma f = rFirmar.get();

                // cifrar con clave aleatoria, añadir a contrato el detalle, coger la clave aleatoria y cifrarla con la clave publica del contratista y anñadirla a clave del contrato

                // falta cifrar con clave aleatoria, cifrar la clave aleatoria con la clave publica del contratista y guardar en base de datos

                Either<String, PublicKey> rGetPub = certificado.getPublicKeyFromCertificateEncoded(contratista.getClave());
                if (rGetPub.isLeft()) {
                    return null;
                } else {

                    //cifrar comprovar

                    Either<String, String> rCifrar = cifrarTextoConClaves.cifrarTextoConClavePublica(rGetPub.get(), f.getFirmaEnBase64());
                    if (rCifrar.isLeft()) {
                        return null;
                    } else {
//                        return rCifrar.get();
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
