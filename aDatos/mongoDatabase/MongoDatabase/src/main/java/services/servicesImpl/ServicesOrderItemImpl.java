package services.servicesImpl;

import dao.DaoOrderItems;
import domain.model.modelHibernate.Order;
import domain.model.modelHibernate.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrderItem;

import java.util.List;

public class ServicesOrderItemImpl implements ServicesOrderItem {

    private DaoOrderItems dOI;

    @Inject
    public ServicesOrderItemImpl(DaoOrderItems dOI) {
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
