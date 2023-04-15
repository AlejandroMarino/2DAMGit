package dao;

import domain.model.Order;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;

public interface DaoOrder {

    Either<Integer, List<Order>> getAll();
    Either<Integer, Map<Order, Double>> getAll(int customerId);
    Either<Integer, Order> get(int id);

    Either<Integer, Order> save(Order order);

    Either<Integer, Void> update(Order order);

    Either<Integer, Void> delete(Order order);
}
