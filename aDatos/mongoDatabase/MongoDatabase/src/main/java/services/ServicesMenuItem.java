package services;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesMenuItem {

    Either<String, List<MenuItem>> getAllOfCustomer(Customer customer);

    Either<String, Void> add(MenuItem menuItem);

    Either<Integer, Void> delete(int id, boolean withOrderItems);
}
