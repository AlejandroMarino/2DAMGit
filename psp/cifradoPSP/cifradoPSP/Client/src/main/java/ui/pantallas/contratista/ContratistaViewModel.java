package ui.pantallas.contratista;

import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.SicarioContrato;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesContratos;
import services.ServicesSicariosContratos;
import services.ServicesUsuarios;

import java.security.KeyStore;

public class ContratistaViewModel {

    private final ServicesUsuarios sU;
    private final ServicesContratos sC;
    private final ServicesSicariosContratos sSC;

    private final ObjectProperty<ContratistaState> state;
    public ReadOnlyObjectProperty<ContratistaState> getState() {
        return state;
    }

    @Inject
    public ContratistaViewModel(ServicesUsuarios sU, ServicesContratos sC, ServicesSicariosContratos sSC) {
        this.sU = sU;
        this.sC = sC;
        this.sSC = sSC;
        state = new SimpleObjectProperty<>(new ContratistaState(null, null, null, null, null));
    }

    public void initialize(int id){
        getContratos(id);
    }

    private void getContratos(int id){
        sC.getContratosContratista(id)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ContratistaState(null, null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new ContratistaState(either.get(), null, null, null, null));
                            }
                        }
                );
    }

    public void getSicariosOfContrato(Contrato contrato) {
        sU.getSicariosOfContrato(contrato.getId())
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ContratistaState(null, null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new ContratistaState(null, either.get(), null, null, null));
                            }
                        }
                );
    }

    public void getEstado(Contrato contrato, Usuario sicario) {
        sSC.getSicarioContrato(new SicarioContrato(sicario, contrato))
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ContratistaState(null, null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new ContratistaState(null, null, null, either.get().getEstado(), null));
                            }
                        }
                );
    }

    public void getDetalle(Contrato contrato, KeyStore ks) {
        Detalle d = sC.getDetalleContratoContratista(contrato, ks);
        state.setValue(new ContratistaState(null, null, d, null, null));
    }

    public void mostrarMensaje(String s) {
        state.setValue(new ContratistaState(null, null, null, null, s));
    }
}
