package dao.daoMongo;

import domain.model.modelMongo.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface DaoCustomer {

    Either<Integer, List<Customer>> getAll(boolean withOrders);

    Either<Integer, Customer> getCustomerById(String id);

    Either<Integer, Void> save(Customer customer);

    Either<Integer, Void> update(Customer customer);

    Either<Integer, Void> delete(String id);
}
