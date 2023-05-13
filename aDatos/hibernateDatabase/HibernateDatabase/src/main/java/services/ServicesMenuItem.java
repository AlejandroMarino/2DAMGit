package services;

import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesMenuItem {

    Either<String, List<String>> getMenuItemsOrderedByCustomer(int customerId);

    Either<String, Void> save(MenuItem menuItem);

    Either<Integer, Void> delete(MenuItem menuItem, boolean withOrders);
}
