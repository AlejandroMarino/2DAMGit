package dao.daoMongo;

import domain.model.modelMongo.MenuItem;
import domain.model.modelMongo.Order;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrderItem {

    Either<Integer, List<OrderItem>> getAll(Order order);

    Either<Integer, List<OrderItem>> getAll(MenuItem menuItem);
}
