package services.servicesImpl;

import dao.daoMongo.DaoMenuItems;
import dao.daoMongo.DaoOrder;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import domain.model.modelMongo.Order;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesOrder;

import java.time.LocalDate;
import java.util.List;

public class ServicesOrderImpl implements ServicesOrder {

    private final DaoOrder dO;

    private final DaoMenuItems dMI;

    @Inject
    public ServicesOrderImpl(DaoOrder dO, DaoMenuItems dMI) {
        this.dO = dO;
        this.dMI = dMI;
    }


    @Override
    public Either<String, List<Order>> getAll(Customer customer) {
        Either<Integer, List<Order>> r = dO.getAll(customer);
        if (r.isLeft() && r.getLeft() == -2){
            return Either.left("Customer not found");
        } else if ((r.isLeft() && r.getLeft() == -3) || r.get().isEmpty()) {
            return Either.left("The customer has no orders");
        } else if (r.isLeft()) {
            return Either.left("Error getting orders");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Void> add(Customer customer) {
        List<OrderItem> orderItems = customer.getOrders().get(0).getOrderItems();
        Either<Integer, MenuItem> mi1 = dMI.get(orderItems.get(0).getName());
        Either<Integer, MenuItem> mi2 = dMI.get(orderItems.get(1).getName());
        if (mi1.isRight() && mi2.isRight()) {
            double price1 = mi1.get().getPrice() * orderItems.get(0).getQuantity();
            double price2 = mi2.get().getPrice() * orderItems.get(1).getQuantity();
            int id1 = mi1.get().get_id();
            int id2 = mi2.get().get_id();
            Order o = customer.getOrders().get(0);
            o.setTotal(price1 + price2);
            o.setDate(LocalDate.now().toString());
            o.getOrderItems().get(0).setMenuItemId(id1);
            o.getOrderItems().get(1).setMenuItemId(id2);
            customer.setOrders(List.of(o));
            Either<Integer, Void> r = dO.save(customer);
            if (r.isLeft()) {
                return Either.left("Error adding order");
            } else {
                return Either.right(r.get());
            }
        } else {
            return Either.left("A menu item was not found");
        }
    }
}
