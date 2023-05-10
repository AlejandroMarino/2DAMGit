package services;

import domain.model.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesCustomer {

    Either<String, List<Customer>> getAll();

    Either<String, Customer> get(int id);

    Either<String, Customer> add(Customer customer);

    Either<String, Customer> update(Customer customer);

    Either<String, Customer> delete(int id);
}
