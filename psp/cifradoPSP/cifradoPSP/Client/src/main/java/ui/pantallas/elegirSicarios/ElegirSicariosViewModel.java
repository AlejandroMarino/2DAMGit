package ui.pantallas.elegirSicarios;

import asymmetric.CifrarTextoConClaves;
import asymmetric.KeyStore;
import domain.models.Contrato;
import domain.models.SicarioContrato;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesSicariosContratos;
import services.ServicesUsuarios;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

public class ElegirSicariosViewModel {

    private final ServicesSicariosContratos sSC;
    private final ServicesUsuarios sU;

    private final KeyStore keyStore;
    private final CifrarTextoConClaves cifrarTextoConClaves;


    private final ObjectProperty<ElegirSicariosState> state;

    public ReadOnlyObjectProperty<ElegirSicariosState> getState() {
        return state;
    }

    @Inject
    public ElegirSicariosViewModel(ServicesSicariosContratos sSC, ServicesUsuarios sU, KeyStore keyStore, CifrarTextoConClaves cifrarTextoConClaves) {
        this.sSC = sSC;
        this.sU = sU;
        this.keyStore = keyStore;
        this.cifrarTextoConClaves = cifrarTextoConClaves;
        state = new SimpleObjectProperty<>(new ElegirSicariosState(null, null, false));
    }

    public void initialize(Contrato contrato) {
        getSicarios(contrato);
    }

    private void getSicarios(Contrato contrato) {

        sU.getSicariosFilterHabilidad(contrato.getHabilidad())
                .observeOn(Schedulers.single())
                .subscribe(
                        eith -> {
                            if (eith.isLeft()) {
                                state.setValue(new ElegirSicariosState(null, eith.getLeft(), false));
                            } else {
                                state.setValue(new ElegirSicariosState(eith.get(), null, false));
                            }
                        }
                );
    }

    public void elegirSicarios(List<Usuario> usuarios, Contrato contrato, Usuario contratista, java.security.KeyStore ks) {
        Either<String, PrivateKey> rGetPK = keyStore.getPrivateKeyFromKeyStore(ks, contratista.getPassword());
        if (rGetPK.isLeft()) {
            state.setValue(new ElegirSicariosState(null, rGetPK.getLeft(), false));
        } else {
            Either<String, String> rDescifrar = cifrarTextoConClaves.descifrarTextoConClavePrivada(rGetPK.get(), contrato.getClave());
            if (rDescifrar.isLeft()) {
                state.setValue(new ElegirSicariosState(null, rDescifrar.getLeft(), false));
            } else {
                List<SicarioContrato> l = usuarios.stream().map(usuario -> {
                    Either<String, PublicKey> rConvertPub = keyStore.convertBase64ToPublicKey(usuario.getClave());
                    if (rConvertPub.isLeft()) {
                        return null;
                    } else {
                        Either<String, String> rCifrar = cifrarTextoConClaves.cifrarTextoConClavePublica(rConvertPub.get(), rDescifrar.get());
                        if (rCifrar.isLeft()) {
                            return null;
                        } else {
                            return new SicarioContrato(usuario, contrato, rCifrar.get());
                        }
                    }
                }).toList();
                sSC.add(l)
                        .observeOn(Schedulers.single())
                        .subscribe(
                                either -> {
                                    if (either.isLeft()) {
                                        state.setValue(new ElegirSicariosState(null, either.getLeft(), false));
                                    } else {
                                        state.setValue(new ElegirSicariosState(null, "sicarios añadidos", true));
                                    }
                                }
                        );
            }
        }
    }

    public void mostrarMensaje(String s) {
        state.setValue(new ElegirSicariosState(null, s, false));
    }
}
