package services.servicesImpl;

import dao.DaoMenuItem;
import domain.model.modelHibernate.Customer;
import domain.model.modelHibernate.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesMenuItem;

import java.util.List;

public class ServicesMenuItemImpl implements ServicesMenuItem {

    private final DaoMenuItem dMI;

    @Inject
    public ServicesMenuItemImpl(DaoMenuItem dMI) {
        this.dMI = dMI;
    }


    @Override
    public Either<String, List<String>> getMenuItemsOrderedByCustomer(int customerId) {
        Either<Integer, List<String>> l = dMI.getAll(new Customer(customerId));
        if (l.isLeft()) {
            return Either.left("Error getting all menu items");
        } else {
            return Either.right(l.get());
        }
    }

    @Override
    public Either<String, Void> save(MenuItem menuItem) {
        Either<Integer, Void> l = dMI.save(menuItem);
        if (l.isLeft()) {
            return Either.left("Error saving menu item");
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<Integer, Void> delete(MenuItem menuItem, boolean withOrders) {
        return dMI.delete(menuItem, withOrders);
    }
}
