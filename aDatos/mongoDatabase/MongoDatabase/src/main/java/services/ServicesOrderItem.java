package services;

import domain.model.modelMongo.Order;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrderItem {

    Either<String, List<OrderItem>> getAllOfOrder(Order order);
}
