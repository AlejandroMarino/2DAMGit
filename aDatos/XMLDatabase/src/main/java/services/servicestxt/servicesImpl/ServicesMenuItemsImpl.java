package services.servicestxt.servicesImpl;

import dao.daotxt.DaoMenuItems;
import domain.model.txt.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.servicestxt.ServicesMenuItems;

import java.util.List;

public class ServicesMenuItemsImpl implements ServicesMenuItems {

    private final DaoMenuItems daoMI;

    @Inject
    public ServicesMenuItemsImpl(DaoMenuItems daoMI) {
        this.daoMI = daoMI;
    }

    @Override
    public Either<String, List<MenuItem>> getAllMenuItems() {
        Either<Integer, List<MenuItem>> result = daoMI.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting menu items");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public MenuItem getMenuItem(String name) {
        Either<Integer, List<MenuItem>> result = daoMI.getAll();
        if (result.isLeft()) {
            return null;
        } else {
            return result.get().stream().filter(menuItem -> menuItem.getName().toLowerCase().equals(name)).findFirst().orElse(null);
        }
    }
}
