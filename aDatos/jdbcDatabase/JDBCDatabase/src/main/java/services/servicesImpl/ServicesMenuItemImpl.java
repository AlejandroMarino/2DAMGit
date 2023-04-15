package services.servicesImpl;

import dao.DaoQueries;
import domain.model.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesMenuItem;

import java.util.Map;

public class ServicesMenuItemImpl implements ServicesMenuItem {

    private final DaoQueries dQ;

    @Inject
    public ServicesMenuItemImpl(DaoQueries dQ) {
        this.dQ = dQ;
    }

    @Override
    public Either<String, Map<MenuItem, Integer>> getMostOrderedItem() {
        Either<Integer, Map<MenuItem, Integer>> result = dQ.getMostOrderedItem();
        if (result.isLeft()) {
            if (result.getLeft() == -2) {
                return Either.left("No orders found");
            }else {
                return Either.left("Error while getting most ordered item");
            }
        } else {
            return Either.right(result.get());
        }
    }
}
