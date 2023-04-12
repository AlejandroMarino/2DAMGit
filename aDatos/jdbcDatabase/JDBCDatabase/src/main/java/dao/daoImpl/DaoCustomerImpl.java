package dao.daoImpl;

import config.Configuration;
import dao.DaoCustomer;
import domain.model.Customer;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import jakarta.inject.Inject;

@Log4j2
public class DaoCustomerImpl implements DaoCustomer {

    private final Configuration config;

    @Inject
    public DaoCustomerImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<Customer>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, Customer> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, Void> save(Customer customer) {
        return null;
    }

    @Override
    public Either<Integer, Void> update(Customer customer) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(Customer customer) {
        return null;
    }
}
