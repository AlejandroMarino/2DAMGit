package cliente.ui.pantallas.games;

import cliente.common.Constants;
import cliente.services.ServicesGames;
import cliente.services.ServicesShops;
import domain.models.Game;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class GamesViewModel {

    private final ServicesGames svG;
    private final ServicesShops svS;

    private final ObjectProperty<GamesState> state;

    public ReadOnlyObjectProperty<GamesState> getState() {
        return state;
    }

    @Inject
    public GamesViewModel(ServicesGames svG, ServicesShops svS) {
        this.svG = svG;
        this.svS = svS;
        state = new SimpleObjectProperty<>(new GamesState(null, null, null, null));
    }

    public void initialize() {
        getAllGames();
        getAllShops();
    }

    public void getAllGames() {
        state.setValue(new GamesState(null, null, null, null));
        svG.getAllGames()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new GamesState(either.get(), null, null, null));
                            }
                        }
                );
    }

    public void getAllShops() {
        state.setValue(new GamesState(null, null, null, null));
        svS.getAllShops()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new GamesState(null, either.get(), null, null));
                            }
                        }
                );
    }

    public void getShop(int id) {
        state.setValue(new GamesState(null, null, null, null));
        svS.getShop(id)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                state.setValue(new GamesState(null, null, either.get(), null));
                            }
                        }
                );
    }

    public void search(String name) {
        state.setValue(new GamesState(null, null, null, null));
        svG.filterByName(name)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                List<Game> games = either.get();
                                if (games.isEmpty()) {
                                    state.setValue(new GamesState(null, null, null, Constants.NO_GAME_FOUND_WITH_THIS_NAME));
                                } else {
                                    state.setValue(new GamesState(games, null, null, null));
                                }
                            }
                        }
                );
    }

    public void filterByShop(int id) {
        state.setValue(new GamesState(null, null, null, null));
        svG.getAllOfShop(id)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                List<Game> games = either.get();
                                if (games.isEmpty()) {
                                    state.setValue(new GamesState(null, null, null, Constants.NO_GAME_FOUND_IN_THIS_SHOP));
                                } else {
                                    state.setValue(new GamesState(games, null, null, null));
                                }
                            }
                        }
                );
    }

    public void add(Game game) {
        state.setValue(new GamesState(null, null, null, null));
        svG.addGame(game)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                getAllGames();
                            }
                        }
                );
    }

    public void delete(int id) {
        state.setValue(new GamesState(null, null, null, null));
        svG.deleteGame(id)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                getAllGames();
                            }
                        }
                );
    }

    public void update(Game game) {
        state.setValue(new GamesState(null, null, null, null));
        svG.updateGame(game)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new GamesState(null, null, null, either.getLeft()));
                            } else {
                                getAllGames();
                            }
                        }
                );
    }
}
