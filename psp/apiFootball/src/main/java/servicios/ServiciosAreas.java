package servicios;

import dao.DaoRxAreas;
import dao.modelo.AreasRequest;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ServiciosAreas {

    public Single<AreasRequest> getAreas(int valor) {
        DaoRxAreas dao = new DaoRxAreas();

        return Completable.fromAction(
                () -> {

                    if (valor == 8) throw new Exception("error de validacion");
                })
                .andThen(dao.getAreas());
    }
}
