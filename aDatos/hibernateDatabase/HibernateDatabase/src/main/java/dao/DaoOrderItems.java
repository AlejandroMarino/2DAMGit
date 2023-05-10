package dao;

import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrderItems {

    Either<Integer, List<OrderItem>> getAll(Order order);
}
