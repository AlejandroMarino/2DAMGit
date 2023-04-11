package services.servicestxt.servicesImpl;

import dao.daotxt.DaoOrderItems;
import domain.model.txt.Order;
import domain.model.txt.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.servicestxt.ServicesOrderItems;

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
