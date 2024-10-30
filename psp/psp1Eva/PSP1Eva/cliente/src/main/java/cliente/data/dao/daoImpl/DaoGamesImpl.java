package cliente.data.dao.daoImpl;

import cliente.data.dao.DaoGames;
import cliente.data.network.GamesApi;
import com.google.gson.Gson;
import domain.models.Game;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoGamesImpl extends DaoGenerics implements DaoGames {

    private final GamesApi gamesApi;

    @Inject
    public DaoGamesImpl(Gson gson, GamesApi gamesApi) {
        super(gson);
        this.gamesApi = gamesApi;
    }

    @Override
    public Single<Either<String, List<Game>>> getAllGames() {
        return safeSingleApicall(gamesApi.getAllGames())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Game>>> filterByName(String name) {
        return safeSingleApicall(gamesApi.filterByName(name))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, List<Game>>> getAllOfShop(int shopId) {
        return safeSingleApicall(gamesApi.getAllOfShop(shopId))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Game>> addGame(Game game) {
        return safeSingleApicall(gamesApi.addGame(game))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, String>> deleteGame(int id) {
        return safeSingleVoidApicall(gamesApi.deleteGame(id))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Either<String, Game>> updateGame(Game game) {
        return safeSingleApicall(gamesApi.updateGame(game))
                .subscribeOn(Schedulers.io());
    }
}
