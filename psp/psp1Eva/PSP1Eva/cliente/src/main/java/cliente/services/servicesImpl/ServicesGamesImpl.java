package cliente.services.servicesImpl;

import cliente.data.dao.DaoGames;
import cliente.services.ServicesGames;
import domain.models.Game;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesGamesImpl implements ServicesGames {

    private final DaoGames daoGames;

    @Inject
    public ServicesGamesImpl(DaoGames daoGames) {
        this.daoGames = daoGames;
    }

    @Override
    public Single<Either<String, List<Game>>> getAllGames() {
        return daoGames.getAllGames();
    }

    @Override
    public Single<Either<String, List<Game>>> filterByName(String name) {
        return daoGames.filterByName(name);
    }

    @Override
    public Single<Either<String, List<Game>>> getAllOfShop(int shopId) {
        return daoGames.getAllOfShop(shopId);
    }

    @Override
    public Single<Either<String, Game>> addGame(Game game) {
        return daoGames.addGame(game);
    }

    @Override
    public Single<Either<String, String>> deleteGame(int id) {
        return daoGames.deleteGame(id);
    }

    @Override
    public Single<Either<String, Game>> updateGame(Game game) {
        return daoGames.updateGame(game);
    }
}
