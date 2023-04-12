package dao;

import domain.model.Order;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrder {

    Either<Integer, List<Order>> getAll();

    Either<Integer, Order> get(int id);

    Either<Integer, Void> save(Order order);

    Either<Integer, Void> update(Order order);

    Either<Integer, Void> delete(Order order);
}
