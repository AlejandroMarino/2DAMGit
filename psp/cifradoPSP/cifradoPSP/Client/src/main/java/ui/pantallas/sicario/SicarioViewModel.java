package ui.pantallas.sicario;

import asymmetric.Certificado;
import asymmetric.CifrarTextoConClaves;
import asymmetric.Firmar;
import asymmetric.KeyStore;
import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.SicarioContrato;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesContratos;
import services.ServicesSicariosContratos;
import symmetric.Encryption;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SicarioViewModel {

    private final ServicesContratos sC;
    private final ServicesSicariosContratos sSC;

    private final KeyStore keyStore;

    private final CifrarTextoConClaves cifrarTextoConClaves;

    private final Encryption encryption;

    private final Certificado certificado;

    private final Firmar firmar;

    private final ObjectProperty<SicarioState> state;

    public ReadOnlyObjectProperty<SicarioState> getState() {
        return state;
    }

    @Inject
    public SicarioViewModel(ServicesContratos sC, ServicesSicariosContratos sSC, KeyStore keyStore, CifrarTextoConClaves cifrarTextoConClaves, Encryption encryption, Certificado certificado, Firmar firmar) {
        this.sC = sC;
        this.sSC = sSC;
        this.keyStore = keyStore;
        this.cifrarTextoConClaves = cifrarTextoConClaves;
        this.encryption = encryption;
        this.certificado = certificado;
        this.firmar = firmar;
        state = new SimpleObjectProperty<>(new SicarioState(null, null, null, null));
    }

    public void getContratos(Usuario sicario) {
        sC.getContratosSicario(sicario.getId())
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new SicarioState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new SicarioState(either.get(), null, null, null));
                            }
                        }
                );
    }

    public void getDetalle(Contrato contrato, Usuario sicario) {
        Either<String, PrivateKey> rGetPK = keyStore.getPrivateKeyFromKeyStore(sicario, sicario.getPassword());
        if (rGetPK.isLeft()) {
            state.setValue(new SicarioState(null, null, null, rGetPK.getLeft()));
        } else {
            sSC.getSicarioContrato(new SicarioContrato(sicario, contrato))
                    .observeOn(Schedulers.single())
                    .subscribe(
                            either -> {
                                if (either.isLeft()) {
                                    state.setValue(new SicarioState(null, null, null, either.getLeft()));
                                } else {
                                    SicarioContrato sc = either.get();
                                    Either<String, String> rDescifrarClave = cifrarTextoConClaves.descifrarTextoConClavePrivada(rGetPK.get(), sc.getClave());
                                    if (rDescifrarClave.isLeft()) {
                                        state.setValue(new SicarioState(null, null, null, rDescifrarClave.getLeft()));
                                    } else {
                                        String rDecrypt = encryption.decrypt(contrato.getDetalle(), rDescifrarClave.get());
                                        if (rDecrypt == null) {
                                            state.setValue(new SicarioState(null, null, null, "Error al descifrar el contrato"));
                                        } else {

                                            //coger la firma del contrato

                                            Either<String, PublicKey> rGetPubContratista = certificado.getPublicKeyFromCertificateEncoded(contrato.getUsuario().getClave());
                                            if (rGetPubContratista.isLeft()) {
                                                state.setValue(new SicarioState(null, null, null, rGetPubContratista.getLeft()));
                                            } else {
//                                                Either<String, String> rComprobarFirma = firmar.verificarFirma(, rGetPubContratista.get());
//                                                if (rComprobarFirma.isLeft()) {
//                                                    state.setValue(new SicarioState(null, null, null, rComprobarFirma.getLeft()));
//                                                } else {
//                                                    Detalle detalle = sC.getDetalleContrato(rDecrypt);
//                                                    state.setValue(new SicarioState(null, detalle, null, null));
//                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    );
        }
    }

    public void getEstado(Contrato contrato, Usuario sicario) {
        sSC.getSicarioContrato(new SicarioContrato(sicario, contrato))
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new SicarioState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new SicarioState(null, null, either.get().getEstado(), null));
                            }
                        }
                );
    }

    public void getContratosFilterEstado(Usuario sicario, Estado estado) {
        sC.getContratosSicarioFilterEstado(sicario.getId(), estado)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new SicarioState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new SicarioState(either.get(), null, null, null));
                            }
                        }
                );
    }

    public void updateEstado(Usuario sicario, Contrato contrato, Estado estado) {
        SicarioContrato sc = new SicarioContrato(sicario, contrato, estado);
        sSC.update(sc)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new SicarioState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new SicarioState(null, null, either.get().getEstado(), null));
                            }
                        }
                );
    }

    public void mostrarMensaje(String s) {
        state.setValue(new SicarioState(null, null, null, s));
    }


}