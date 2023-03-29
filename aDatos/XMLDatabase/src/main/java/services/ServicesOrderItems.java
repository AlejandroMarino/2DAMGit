package services;

import domain.model.txt.Order;
import domain.model.txt.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrderItems {

    Either<String, List<OrderItem>> getOrderItemsOfOrder(Order order);
}
