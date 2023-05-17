package dao;

import domain.model.modelHibernate.Order;
import domain.model.modelHibernate.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrderItems {

    Either<Integer, List<OrderItem>> getAll(Order order);
}
