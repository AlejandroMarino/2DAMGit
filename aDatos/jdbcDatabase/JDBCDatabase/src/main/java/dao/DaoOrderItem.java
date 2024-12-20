package dao;

import domain.model.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrderItem {

    Either<Integer, List<OrderItem>> getAll();

    Either<Integer, List<OrderItem>> getAll(int orderId);

    Either<Integer, OrderItem> get(int id);

    Either<Integer, Void> save(OrderItem orderItem);

    Either<Integer, Void> update(OrderItem orderItem);

    Either<Integer, Void> delete(OrderItem orderItem);
}
