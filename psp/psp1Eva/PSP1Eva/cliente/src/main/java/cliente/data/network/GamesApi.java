package cliente.data.network;

import domain.models.Game;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface GamesApi {

    @GET("games")
    Single<List<Game>> getAllGames();

    @GET("games/filter_name")
    Single<List<Game>> filterByName(@Query("name") String name);

    @GET("games/filter_shop")
    Single<List<Game>> getAllOfShop(@Query("shop") int shopId);

    @POST("games")
    Single<Game> addGame(Game game);

    @DELETE("games")
    Single<Response<Void>> deleteGame(@Query("id") int id);

    @PUT("games")
    Single<Game> updateGame(Game game);
}
