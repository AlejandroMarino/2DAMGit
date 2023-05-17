package services;

import domain.model.modelHibernate.Customer;
import domain.model.modelHibernate.Order;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrder {

    Either<String, Void> save(Order order);

    Either<String, List<Order>> getAllOfCustomer(Customer customer);

    Either<String, List<String>> getOrdersWithNumberOfItems();
}
