package cliente.ui.pantallas.shops;

import cliente.services.ServicesShops;
import domain.models.Shop;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class ShopsViewModel {

    private final ServicesShops svS;

    private final ObjectProperty<ShopsState> state;

    public ReadOnlyObjectProperty<ShopsState> getState() {
        return state;
    }

    @Inject
    public ShopsViewModel(ServicesShops svS) {
        this.svS = svS;
        state = new SimpleObjectProperty<>(new ShopsState(null, null));
    }

    public void initialize() {
        getAllShops();
    }

    public void getAllShops() {
        state.setValue(new ShopsState(null, null));
        svS.getAllShops()
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ShopsState(null, either.getLeft()));
                            } else {
                                state.setValue(new ShopsState(either.get(), null));
                            }
                        }
                );
    }

    public void search(String name) {
        state.setValue(new ShopsState(null, null));
        svS.filterByName(name)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ShopsState(null, either.getLeft()));
                            } else {
                                List<Shop> shops = either.get();
                                if (shops.isEmpty()) {
                                    state.setValue(new ShopsState(null, "No shop found with this name"));
                                } else {
                                    state.setValue(new ShopsState(either.get(), null));
                                }
                            }
                        }
                );
    }

    public void add(Shop shop) {
        state.setValue(new ShopsState(null, null));
        svS.addShop(shop)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ShopsState(null, either.getLeft()));
                            } else {
                                getAllShops();
                            }
                        }
                );
    }

    public void delete(int id) {
        state.setValue(new ShopsState(null, null));
        svS.deleteShop(id)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ShopsState(null, either.getLeft()));
                            } else {
                                getAllShops();
                            }
                        }
                );
    }

    public void update(Shop shop) {
        state.setValue(new ShopsState(null, null));
        svS.updateShop(shop)
                .observeOn(Schedulers.single())
                .subscribe(
                        either -> {
                            if (either.isLeft()) {
                                state.setValue(new ShopsState(null, either.getLeft()));
                            } else {
                                getAllShops();
                            }
                        }
                );
    }
}
