package cliente.data.network;

import domain.models.Shop;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ShopsApi {


    @GET("shops")
    Single<List<Shop>> getAllShops();

    @GET("shops/filter")
    Single<List<Shop>> filterByName(@Query("name") String name);

    @GET("shops/{id}")
    Single<Shop> getShop(@Path("id") int id);

    @POST("shops")
    Single<Shop> addShop(@Body Shop shop);

    @DELETE("shops")
    Single<Response<Void>> deleteShop(@Query("id") int id);

    @PUT("shops")
    Single<Shop> updateShop(@Body Shop shop);
}
