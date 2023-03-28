package services;

import domain.model.Customer;
import domain.model.MenuItem;
import domain.model.Order;
import io.vavr.control.Either;

import java.util.List;

public interface Services {
    Either<String, List<Customer>> getAllCustomers();
    Either<String, List<Order>> getOrdersByCustomer(String name);
    Boolean deleteCustomer(String name);
    void deleteOrders(List<Order> orders);
    Either<String, List<MenuItem>> getAllMenuItems();
    Either<String, Customer> getCustomer(String name);
    Either<String, Void> addOrder(Customer customer, int table, String name1, int quantity1, String name2, int quantity2);
}
