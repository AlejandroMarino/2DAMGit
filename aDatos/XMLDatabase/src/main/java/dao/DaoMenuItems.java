package dao;

import domain.model.txt.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMenuItems {
    Either<Integer, MenuItem> get(int id);

    Either<Integer, List<MenuItem>> getAll();

    Either<Integer, Void> save(MenuItem menuItem);

    Either<Integer, Void> update(MenuItem menuItem);

    Either<Integer, Void> delete(MenuItem menuItem);
}
