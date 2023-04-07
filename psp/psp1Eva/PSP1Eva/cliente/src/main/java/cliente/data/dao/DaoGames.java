package cliente.data.dao;

import domain.models.Game;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoGames {

    Single<Either<String, List<Game>>> getAllGames();

    Single<Either<String, List<Game>>> filterByName(String name);

    Single<Either<String, List<Game>>> getAllOfShop(int shopId);

    Single<Either<String, Game>> addGame(Game game);

    Single<Either<String, String>> deleteGame(int id);

    Single<Either<String, Game>> updateGame(Game game);
}
