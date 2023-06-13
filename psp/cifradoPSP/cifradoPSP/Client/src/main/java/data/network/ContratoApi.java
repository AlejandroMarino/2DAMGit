package data.network;

import domain.models.Contrato;
import domain.models.Estado;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;

import java.util.List;

public interface ContratoApi {

    @POST("contratos")
    Single<Contrato> add(@Body Contrato contrato);

    @PUT("contratos")
    Single<Contrato> update(@Body Contrato contrato);

    @GET("contratos/contratista")
    Single<List<Contrato>> getContratosContratista(@Query("id")int id);

    @GET("contratos/sicario")
    Single<List<Contrato>> getContratosSicario(@Query("id")int id);

    @GET("contratos/sicarioFilterEstado")
    Single<List<Contrato>> getContratosSicarioFilterEstado(@Query("id")int id, @Query("estado")Estado estado);

    @GET("contratos/{id}")
    Single<Contrato> getContrato(@Path("id")int id);
}
