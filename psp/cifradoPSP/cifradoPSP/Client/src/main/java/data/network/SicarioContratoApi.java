package data.network;

import domain.models.SicarioContrato;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface SicarioContratoApi {

    @POST("sicarios_contratos")
    Single<Response<Void>> add(@Body List<SicarioContrato> sicarioContrato);

    @PUT("sicarios_contratos")
    Single<SicarioContrato> update(@Body SicarioContrato sicarioContrato);

    @GET("sicarios_contratos")
    Single<SicarioContrato> get(@Query("sicario") int idSicario, @Query("contrato") int idContrato);
}
