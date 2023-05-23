package dao.daoMongo;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.Order;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrder {

    Either<Integer, List<Order>> getAll(Customer customer);

    Either<Integer, Void> save(Customer customer);
}
