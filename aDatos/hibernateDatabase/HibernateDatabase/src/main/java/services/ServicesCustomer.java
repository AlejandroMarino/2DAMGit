package services;

import domain.model.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesCustomer {

    Either<String, List<Customer>> getAll(boolean withOrders);

    Either<String, Customer> get(int id);

    Either<String, Void> add(Customer customer);

    Either<String, Void> update(Customer customer);

    Either<String, Void> delete(int id, boolean withOrders);

    String getCustomerSpentMost();
}
