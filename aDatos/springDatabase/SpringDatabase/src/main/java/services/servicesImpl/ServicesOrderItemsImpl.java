package services.servicesImpl;

import dao.DaoMenuItem;
import dao.DaoOrderItems;
import dao.DaoOrders;
import domain.model.spring.Customer;
import domain.model.spring.MenuItem;
import domain.model.spring.Order;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrderItems;

public class ServicesOrderItemsImpl implements ServicesOrderItems {

    private final DaoOrders dO;
    private final DaoOrderItems dOI;
    private final DaoMenuItem dMI;

    @Inject
    public ServicesOrderItemsImpl(DaoOrders dO, DaoOrderItems dOI, DaoMenuItem dMI) {
        this.dO = dO;
        this.dOI = dOI;
        this.dMI = dMI;
    }

    @Override
    public Either<String, Void> addOrderItem(MenuItem menuItem, OrderItem orderItem, Customer customer) {
        Either<Integer, MenuItem> mItem = dMI.get(menuItem.getName());
        if (mItem.isLeft()) {
            return Either.left("Menu item not found");
        } else {
            orderItem.setMenuItemId(mItem.get().getId());
            Either<Integer, Order> order = dO.get(customer);
            if (order.isLeft()) {
                return Either.left("Order not found");
            } else {
                orderItem.setOrderId(order.get().getId());
                Either<Integer, Void> r = dOI.save(orderItem);
                if (r.isLeft()) {
                    return Either.left("Error saving order item");
                }else {
                    return Either.right(null);
                }
            }
        }
    }
}
