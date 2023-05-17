package services;

import domain.model.modelHibernate.Order;
import domain.model.modelHibernate.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrderItem {

    Either<String, List<OrderItem>> getAll(Order order);
}
