package services.servicesImpl;

import dao.DaoMenuItem;
import dao.DaoOrder;
import dao.DaoQueries;
import domain.model.modelHibernate.Customer;
import domain.model.modelHibernate.MenuItem;
import domain.model.modelHibernate.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrder;

import java.util.List;

public class ServicesOrderImpl implements ServicesOrder {

    private final DaoOrder dO;
    private final DaoMenuItem dMI;

    private final DaoQueries dQ;

    @Inject
    public ServicesOrderImpl(DaoOrder dO, DaoMenuItem dMI, DaoQueries dQ) {
        this.dO = dO;
        this.dMI = dMI;
        this.dQ = dQ;
    }

    @Override
    public Either<String, Void> save(Order order) {
        Either<Integer, MenuItem> mI1 = dMI.get(order.getOrderItems().get(0).getMenuItem().getName());
        Either<Integer, MenuItem> mI2 = dMI.get(order.getOrderItems().get(1).getMenuItem().getName());
        if (mI1.isLeft() || mI2.isLeft()) {
            return Either.left("Error getting menu item");
        } else {
            order.getOrderItems().get(0).setMenuItem(mI1.get());
            order.getOrderItems().get(1).setMenuItem(mI2.get());
            Either<Integer, Void> l = dO.save(order);
            if (l.isLeft()) {
                return Either.left("Error saving order");
            } else {
                return Either.right(null);
            }
        }
    }

    @Override
    public Either<String, List<Order>> getAllOfCustomer(Customer customer) {
        Either<Integer, List<Order>> r = dO.getAll(customer);
        if (r.isLeft()) {
            return Either.left("Error getting orders");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, List<String>> getOrdersWithNumberOfItems() {
        Either<Integer, List<String>> r = dQ.getOrdersWithNumberOfItems();
        if (r.isLeft()) {
            return Either.left("Error getting orders");
        } else {
            return Either.right(r.get());
        }
    }
}
