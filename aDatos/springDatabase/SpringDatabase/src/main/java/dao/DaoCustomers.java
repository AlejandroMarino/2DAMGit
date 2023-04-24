package dao;

import domain.model.spring.Customer;
import io.vavr.control.Either;

public interface DaoCustomers {

    Either<Integer, Customer> get(int id);


}
