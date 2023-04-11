package dao.daotxt;

import domain.model.txt.Order;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrders {

    Either<Integer, Order> get(int id);

    Either<Integer, List<Order>> getAll();

    Either<Integer, Void> save(Order order);

    Either<Integer, Void> update(Order order);

    Either<Integer, Void> delete(Order order);
}
