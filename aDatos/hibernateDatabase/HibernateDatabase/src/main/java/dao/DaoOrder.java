package dao;

import domain.model.Customer;
import domain.model.Order;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrder {
    Either<Integer, Void> save(Order order);

    Either<Integer, List<Order>> getAll(Customer customer);
}
