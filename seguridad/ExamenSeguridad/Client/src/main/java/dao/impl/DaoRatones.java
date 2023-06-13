package dao.impl;

import dao.retrofit.calls.APIRatones;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Raton;

import java.util.List;

public class DaoRatones extends DaoGenerics{
    private final APIRatones api;

    @Inject
    public DaoRatones(APIRatones api) {
        this.api = api;
    }

    public Single<Either<String, List<Raton>>> getRatones(){
        return this.safeSingleApicall(api.getRatones());
    }

    public Single<Either<String, Raton>> addRaton(Raton raton){
        return this.safeSingleApicall(api.addRaton(raton));
    }
}
