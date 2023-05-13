package dao;

import domain.model.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface DaoCustomer {

    Either<Integer, List<Customer>> getAll(boolean withOrders);

    Either<Integer, Customer> get(int id);

    Either<Integer, Customer> save(Customer customer);

    Either<Integer, Void> update(Customer customer);

    Either<Integer, Void> delete(Customer customer, boolean withOrders);

}
