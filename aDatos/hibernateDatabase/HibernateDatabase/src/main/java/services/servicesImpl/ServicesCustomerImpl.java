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

    private final DaoQueries dQ;

    @Inject
    public ServicesCustomerImpl(DaoCustomer dC, DaoQueries dQ) {
        this.dC = dC;
        this.dQ = dQ;
    }

    @Override
    public Either<String, List<Customer>> getAll(boolean withOrders) {
        Either<Integer, List<Customer>> r = dC.getAll(withOrders);
        if (r.isLeft()) {
            return Either.left("Error getting all customers");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Customer> get(int id) {
        Either<Integer, Customer> r = dC.get(id);
        if (r.isLeft()) {
            return Either.left("Customer not found");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Void> add(Customer customer) {
        Either<Integer, Customer> r = dC.save(customer);
        if (r.isLeft()) {
            return Either.left("Error adding customer");
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<String, Void> update(Customer customer) {
        Either<Integer, Void> r = dC.update(customer);
        if (r.isLeft()) {
            return Either.left("Error updating customer");
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<String, Void> delete(int id, boolean withOrders) {
        Either<Integer, Void> r = dC.delete(new Customer(id), withOrders);
        if (r.isLeft()) {
            if (r.getLeft() == -1) {
                return Either.left("Customer not found");
            } else if (r.getLeft() == -2) {
                return Either.left("The Customer has orders");
            } else {
                return Either.left("The Customer can't be deleted");
            }
        } else {
            return Either.right(null);
        }
    }

    @Override
    public String getCustomerSpentMost() {
        Either<Integer, String> r = dQ.getCustomerSpentMost();
        if (r.isLeft()) {
            return "Error getting customer";
        } else {
            return r.get();
        }
    }


}
