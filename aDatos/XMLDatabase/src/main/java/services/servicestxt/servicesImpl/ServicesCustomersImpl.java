package services.servicestxt.servicesImpl;

import dao.daotxt.DaoCustomers;
import dao.daotxt.DaoOrders;
import domain.model.txt.Customer;
import domain.model.txt.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.servicestxt.ServicesCustomers;

import java.util.List;

public class ServicesCustomersImpl implements ServicesCustomers {

    private final DaoCustomers daoC;
    private final DaoOrders daoO;

    @Inject
    public ServicesCustomersImpl(DaoCustomers daoC, DaoOrders daoO) {
        this.daoC = daoC;
        this.daoO = daoO;
    }

    @Override
    public Either<String, List<Customer>> getAllCustomers() {
        Either<Integer, List<Customer>> result = daoC.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting all customers");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Boolean deleteCustomer(String name) {
        Either<String, Customer> customer = getCustomer(name);
        if (customer.isLeft()) {
            return false;
        } else {
            Customer c = customer.get();
            Either<Integer, List<Order>> orders = daoO.getAll();
            if (orders.isLeft()) {
                return false;
            } else {
                Either<Integer, Void> result = daoC.delete(c);
                return !result.isLeft();
            }
        }
    }

    @Override
    public Either<String, Customer> getCustomer(String name) {
        Either<Integer, List<Customer>> customers = daoC.getAll();
        if (customers.isLeft()) {
            return Either.left("Error while getting customers");
        } else {
            Customer c = customers.get().stream().filter(customer -> customer.getFirstName().toLowerCase().equals(name))
                    .findFirst().orElse(null);
            if (c == null) {
                return Either.left("Customer not found");
            } else {
                return Either.right(c);
            }
        }
    }

}
