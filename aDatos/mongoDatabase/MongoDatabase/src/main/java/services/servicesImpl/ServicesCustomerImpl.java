package services.servicesImpl;

import dao.daoMongo.DaoCustomer;
import dao.daoMongo.DaoOrder;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import services.ServicesCustomer;

import java.util.List;

public class ServicesCustomerImpl implements ServicesCustomer {

    private final DaoCustomer dC;
    private final DaoOrder dO;

    @Inject
    public ServicesCustomerImpl(DaoCustomer dC, DaoOrder dO) {
        this.dC = dC;
        this.dO = dO;
    }


    @Override
    public Either<String, List<Customer>> getAll(boolean withOrders) {
        Either<Integer, List<Customer>> r = dC.getAll(withOrders);
        if (r.isLeft()) {
            return Either.left("Error getting customers");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Customer> get(String id) {
        Either<Integer, Customer> r = dC.get(id);
        if (r.isLeft()) {
            if (r.getLeft() == -2) {
                return Either.left("Customer not found");
            } else {
                return Either.left("Error getting customer");
            }
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Void> save(Customer customer) {
        Either<Integer, Void> r = dC.save(customer);
        if (r.isLeft()) {
            return Either.left("Error saving customers");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Void> update(Customer customer) {
        Either<Integer, Void> r = dC.update(customer);
        if (r.isLeft()) {
            return Either.left("Error updating customers");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<Integer, Void> delete(String id, boolean withOrders) {
        Either<Integer, List<Order>> r = dO.getAll(new Customer(new ObjectId(id)));
        if (r.isRight() && (r.get() == null || !r.get().isEmpty()) && !withOrders) {
            return Either.left(-2);
        } else {
            Either<Integer, Void> r2 = dC.delete(id);
            if (r2.isLeft()) {
                return Either.left(-1);
            } else {
                return Either.right(r2.get());
            }
        }
    }
}
