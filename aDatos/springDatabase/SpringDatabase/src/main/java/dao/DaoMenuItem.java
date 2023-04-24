package dao;

import domain.model.spring.MenuItem;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;

public interface DaoMenuItem {

    MenuItem get(OrderItem orderItem);

    Either<Integer, MenuItem> get(String name);

    Either<Integer, Void> update(MenuItem menuItem);

}
