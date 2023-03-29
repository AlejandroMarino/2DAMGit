package services.servicesImpl;

import dao.DaoOrderItems;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrderItems;

import java.util.List;

public class ServicesOrderItemsImpl implements ServicesOrderItems {

    private final DaoOrderItems daoOI;

    @Inject
    public ServicesOrderItemsImpl(DaoOrderItems daoOI) {
        this.daoOI = daoOI;
    }
    @Override
    public Either<String, List<OrderItem>> getOrderItemsOfOrder(Order order) {
        Either<Integer, List<OrderItem>> result = daoOI.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting order items");
        } else {
            List<OrderItem> orderItems = result.get().stream().filter(orderItem -> orderItem.getOrderId() == order.getId()).toList();
            return Either.right(orderItems);
        }
    }
}
