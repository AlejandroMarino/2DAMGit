package dao;

import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMenuItem {

    Either<Integer, List<MenuItem>> getAll();

    Either<Integer, MenuItem> get(int id);

    Either<Integer, Void> save(MenuItem menuItem);

    Either<Integer, Void> update(MenuItem menuItem);

    Either<Integer, Void> delete(MenuItem menuItem);
}
