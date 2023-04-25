package dao;


import domain.model.spring.Order;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrderItems {

    List<OrderItem> getAll(Order order);

    Either<Integer, OrderItem> save(OrderItem orderItem);
}
