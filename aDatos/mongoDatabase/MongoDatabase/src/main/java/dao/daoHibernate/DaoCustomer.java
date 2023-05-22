package dao.daoHibernate;

import domain.model.modelHibernate.Customer;
import io.vavr.control.Either;

import java.util.List;

public interface DaoCustomer {

    Either<Integer, List<Customer>> getAll(boolean withOrders);

}
