package cliente.data.network;

import cliente.common.Constants;
import domain.models.Game;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface GamesApi {

    @GET(Constants.GAMES)
    Single<List<Game>> getAllGames();

    @GET(Constants.GAMES_FILTER_NAME)
    Single<List<Game>> filterByName(@Query(Constants.NAME) String name);

    @GET(Constants.GAMES_FILTER_SHOP)
    Single<List<Game>> getAllOfShop(@Query(Constants.SHOP) int shopId);

    @POST(Constants.GAMES)
    Single<Game> addGame(@Body Game game);

    @DELETE(Constants.GAMES)
    Single<Response<Void>> deleteGame(@Query(Constants.ID) int id);

    @PUT(Constants.GAMES)
    Single<Game> updateGame(@Body Game game);
}
