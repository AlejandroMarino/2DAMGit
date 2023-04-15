package services;

import domain.model.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesCustomer {

    Either<String, List<Customer>> getAllCustomers();

    Either<String, List<Customer>> get2CSpentLessMoney();

    Either<String, Void> deleteCustomer(int id);
}
