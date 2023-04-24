package services;


import domain.model.spring.Order;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;

public interface ServicesOrders {

    Either<String, Map<Order, List<OrderItem>>> getAllOrdersWItems();
}
