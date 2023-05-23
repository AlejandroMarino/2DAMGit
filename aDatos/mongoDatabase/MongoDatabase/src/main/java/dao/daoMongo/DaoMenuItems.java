package dao.daoMongo;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMenuItems {

    Either<Integer, List<MenuItem>> getAll(Customer customer);

    Either<Integer, MenuItem> get(String name);

    Either<Integer, Void> save(MenuItem menuItem);

    Either<Integer, Void> delete(int id, boolean hasOrderItems);

}
