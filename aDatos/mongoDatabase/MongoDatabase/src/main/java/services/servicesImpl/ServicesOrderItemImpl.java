package services.servicesImpl;

import dao.daoMongo.DaoOrderItem;
import domain.model.modelMongo.MenuItem;
import domain.model.modelMongo.Order;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrderItem;

import java.util.List;

public class ServicesOrderItemImpl implements ServicesOrderItem {

    private final DaoOrderItem dOI;

    @Inject
    public ServicesOrderItemImpl(DaoOrderItem dOI) {
        this.dOI = dOI;
    }


    @Override
    public Either<String, List<OrderItem>> getAllOfOrder(Order order) {
        Either<Integer, List<OrderItem>> r = dOI.getAll(order);
        if (r.isLeft()) {
            return Either.left("Error getting menu items");
        } else {
            return Either.right(r.get());
        }
    }
}
