package services.servicesImpl;

import dao.DaoCustomer;
import dao.DaoQueries;
import domain.model.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesCustomer;

import java.util.List;

public class ServicesCustomerImpl implements ServicesCustomer {

    private final DaoCustomer dC;

    private DaoQueries dQ;

    @Inject
    public ServicesCustomerImpl(DaoCustomer dC, DaoQueries dQ) {
        this.dC = dC;
        this.dQ = dQ;
    }


    @Override
    public Either<String, List<Customer>> getAllCustomers() {
        Either<Integer, List<Customer>> result = dC.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting all customers");
        } else if (result.get().isEmpty()) {
            return Either.left("There are no customers");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Either<String, List<Customer>> get2CSpentLessMoney() {
        Either<Integer, List<Customer>> result = dQ.get2CSpentLessMoney();
        if (result.isLeft()) {
            return Either.left("Error while getting 2 customers who spent less money");
        } else if (result.get().isEmpty()) {
            return Either.left("There are no customers");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Either<String, Void> deleteCustomer(int id) {
        Either<Integer, Void> result = dC.delete(new Customer(id));
        if (result.isLeft()) {
            if (result.getLeft() == -2) {
                return Either.left("Error while doing rollback");
            }else if (result.getLeft() == -3) {
                return Either.left("Error closing connection");
            }else{
                return Either.left("Error while deleting customer");
            }
        } else {
            return Either.right(null);
        }
    }
}
