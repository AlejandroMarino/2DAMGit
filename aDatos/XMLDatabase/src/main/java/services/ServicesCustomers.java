package services;

import domain.model.txt.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesCustomers {
    Either<String, List<Customer>> getAllCustomers();

    Boolean deleteCustomer(String name);

    Either<String, Customer> getCustomer(String name);
}
