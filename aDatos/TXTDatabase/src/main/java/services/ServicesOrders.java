package services;

import domain.model.Customer;
import domain.model.Order;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrders {

    Either<String, List<Order>> getOrdersByCustomer(String name);
    void deleteOrders(List<Order> orders);
    Either<String, Void> addOrder(Customer customer, int table, String name1, int quantity1, String name2, int quantity2);
}
