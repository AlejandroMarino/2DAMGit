package dao;

import domain.model.Customer;
import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMenuItem {

    Either<Integer, List<String>> getAll(Customer customer);

    Either<Integer, MenuItem> get(String name);

    Either<Integer, Void> save(MenuItem menuItem);

    Either<Integer, Void> delete(MenuItem menuItem, boolean withOrders);
}
