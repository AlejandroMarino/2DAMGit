package dao.daoMongo;

import domain.model.modelMongo.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface DaoCustomer {

    Either<Integer, List<Customer>> getAll();
}
