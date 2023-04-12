package cliente.data.network;

import cliente.common.Constants;
import domain.models.Shop;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ShopsApi {

    @GET(Constants.SHOPS)
    Single<List<Shop>> getAllShops();

    @GET(Constants.SHOPS_FILTER)
    Single<List<Shop>> filterByName(@Query(Constants.NAME) String name);

    @GET(Constants.SHOPS_ID)
    Single<Shop> getShop(@Path(Constants.ID) int id);

    @POST(Constants.SHOPS)
    Single<Shop> addShop(@Body Shop shop);

    @DELETE(Constants.SHOPS)
    Single<Response<Void>> deleteShop(@Query(Constants.ID) int id);

    @PUT(Constants.SHOPS)
    Single<Shop> updateShop(@Body Shop shop);
}
