package services;

import domain.model.modelMongo.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesCustomer {
    Either<String, List<Customer>> getAll(boolean withOrders);

    Either<String, Customer> get(String id);

    Either<String, Void> save(Customer customer);

    Either<String, Void> update(Customer customer);

    Either<Integer, Void> delete(String id, boolean withOrders);
}
