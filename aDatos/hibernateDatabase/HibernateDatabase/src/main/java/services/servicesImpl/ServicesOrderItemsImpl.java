package services.servicesImpl;

import dao.DaoOrderItems;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrderItems;

import java.util.List;

public class ServicesOrderItemsImpl implements ServicesOrderItems {

    private DaoOrderItems dOI;

    @Inject
    public ServicesOrderItemsImpl(DaoOrderItems dOI) {
        this.dOI = dOI;
    }


    @Override
    public Either<String, List<OrderItem>> getAll(Order order) {
        Either<Integer, List<OrderItem>> l = dOI.getAll(order);
        if(l.isLeft()) {
            return Either.left("Error getting all order items");
        }else {
            return Either.right(l.get());
        }
    }
}
