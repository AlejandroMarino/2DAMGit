package services.servicesImpl;

import dao.DaoMenuItem;
import domain.model.spring.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesMenuItems;

public class ServicesMenuItemsImpl implements ServicesMenuItems {

    private final DaoMenuItem dMI;

    @Inject
    public ServicesMenuItemsImpl(DaoMenuItem dMI) {
        this.dMI = dMI;
    }

    @Override
    public Either<String, Void> modifyPrice(MenuItem menuItem) {
        Either<Integer, MenuItem> mItem = dMI.get(menuItem.getName());
        if (mItem.isLeft()) {
            return Either.left("Menu item not found");
        } else {
            mItem.get().setPrice(menuItem.getPrice());
            dMI.update(mItem.get());
            return Either.right(null);
        }
    }
}
