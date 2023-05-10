package services.servicesImpl;

import dao.DaoCustomer;
import domain.model.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesCustomer;

import java.util.List;

public class ServicesCustomerImpl implements ServicesCustomer {

    private final DaoCustomer dC;

    @Inject
    public ServicesCustomerImpl(DaoCustomer dC) {
        this.dC = dC;
    }

    @Override
    public Either<String, List<Customer>> getAll() {
        return null;
    }

    @Override
    public Either<String, Customer> get(int id) {
        return null;
    }

    @Override
    public Either<String, Customer> add(Customer customer) {
        return null;
    }

    @Override
    public Either<String, Customer> update(Customer customer) {
        return null;
    }

    @Override
    public Either<String, Customer> delete(int id) {
        return null;
    }
}
