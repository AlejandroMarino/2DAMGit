package dao;

import domain.model.spring.Customer;
import domain.model.spring.Order;
import io.vavr.control.Either;

import java.util.List;

public interface DaoOrders {

    List<Order> getAll();

    Either<Integer, List<Order>> getAll(boolean paid);

    Either<Integer, Order> get(Customer customer);

    Either<Integer, Void> delete(int id);
}
