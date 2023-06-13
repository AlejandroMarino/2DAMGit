package dao.impl;

import dao.retrofit.calls.APIInformes;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Informe;

import java.util.List;

public class DaoInformes extends DaoGenerics{
    private final APIInformes api;

    @Inject
    public DaoInformes(APIInformes api) {
        this.api = api;
    }

    public Single<Either<String, List<Informe>>> getInformes(){
        return this.safeSingleApicall(api.getInformes());
    }

    public Single<Either<String, Informe>> getInforme(int id){
        return this.safeSingleApicall(api.getInforme(id));
    }

    public Single<Either<String, Informe>> addInforme(Informe informe){
        return this.safeSingleApicall(api.addInforme(informe));
    }
}
