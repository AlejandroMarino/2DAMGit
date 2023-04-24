package services.servicesImpl;

import dao.DaoOrderItems;
import dao.DaoOrders;
import domain.model.spring.Order;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesOrdersImpl implements ServicesOrders {

    private final DaoOrders daoO;
    private final DaoOrderItems daoOI;

    @Inject
    public ServicesOrdersImpl(DaoOrders daoO, DaoOrderItems daoOI) {
        this.daoO = daoO;
        this.daoOI = daoOI;
    }


    @Override
    public Either<String, Map<Order, List<OrderItem>>> getAllOrdersWItems() {
        Map<Order, List<OrderItem>> map = new HashMap<>();
        List<Order> orders = daoO.getAll();
        if (orders.isEmpty()) {
            return Either.left("No orders found");
        } else {
            for (Order order : orders) {
                List<OrderItem> orderItems = daoOI.getAll(order);
                map.put(order, orderItems);
            }
        }
        return Either.right(map);
    }
}
