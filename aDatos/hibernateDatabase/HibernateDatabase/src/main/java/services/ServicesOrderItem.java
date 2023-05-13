package services;

import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrderItem {

    Either<String, List<OrderItem>> getAll(Order order);
}
