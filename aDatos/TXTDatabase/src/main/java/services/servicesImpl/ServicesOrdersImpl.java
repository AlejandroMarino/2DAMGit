package services.servicesImpl;

import dao.DaoOrderItems;
import dao.DaoOrders;
import domain.model.Customer;
import domain.model.MenuItem;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesCustomers;
import services.ServicesMenuItems;
import services.ServicesOrderItems;
import services.ServicesOrders;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class ServicesOrdersImpl implements ServicesOrders {

    private final DaoOrders daoO;
    private final DaoOrderItems daoOI;
    private final ServicesCustomers sC;
    private final ServicesOrderItems sOI;
    private final ServicesMenuItems sMI;

    @Inject
    public ServicesOrdersImpl(DaoOrders daoO, ServicesCustomers sC, ServicesOrderItems sOI, DaoOrderItems daoOI, ServicesMenuItems sMI) {
        this.daoO = daoO;
        this.sC = sC;
        this.sOI = sOI;
        this.daoOI = daoOI;
        this.sMI = sMI;
    }

    @Override
    public Either<String, List<Order>> getOrdersByCustomer(String name) {
        Either<Integer, List<Order>> result = daoO.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting orders");
        } else {
            Either<String, Customer> customer = sC.getCustomer(name);
            if (customer.isLeft()) {
                return Either.left(customer.getLeft());
            } else {
                Customer c = customer.get();
                List<Order> orders = result.get().stream().filter(order -> order.getCustomerId() == c.getId()).toList();
                if (orders.isEmpty()){
                    return Either.left("No orders found for customer " + name);
                }
                else {
                    return Either.right(orders);
                }
            }
        }
    }

    @Override
    public void deleteOrders(List<Order> orders) {
        orders.forEach(order -> {
            Either<String, List<OrderItem>> orderItems = sOI.getOrderItemsOfOrder(order);
            if (orderItems.isLeft()) {
                return;
            } else {
                orderItems.get().forEach(daoOI::delete);
            }
            daoO.delete(order);
        });
    }

    @Override
    public Either<String, Void> addOrder(Customer customer, int table, String name1, int quantity1, String name2, int quantity2) {
        Random r = new Random();
        MenuItem item1 = sMI.getMenuItem(name1);
        MenuItem item2 = sMI.getMenuItem(name2);
        if (item1 == null || item2 == null) {
            return Either.left("One or more menu items not found");
        } else {
            double priceItems1 = item1.getPrice() * quantity1;
            double priceItems2 = item2.getPrice() * quantity2;
            double totalPrice = priceItems1 + priceItems2;
            Order order = new Order(r.nextInt(), table, customer.getId(), LocalDate.now(), totalPrice);
            OrderItem orderItem1 = new OrderItem(r.nextInt(), order.getId(), item1.getId(), quantity1, priceItems1);
            OrderItem orderItem2 = new OrderItem(r.nextInt(), order.getId(), item2.getId(), quantity2, priceItems2);
            Either<Integer, Void> rAddItem1 = daoOI.save(orderItem1);
            if (rAddItem1.isLeft()) {
                return Either.left("Error while adding the first order item");
            } else {
                Either<Integer, Void> rAddItem2 = daoOI.save(orderItem2);
                if (rAddItem2.isLeft()) {
                    daoOI.delete(orderItem1);
                    return Either.left("Error while adding the second order item");
                } else {
                    Either<Integer, Void> rAddOrder = daoO.save(order);
                    if (rAddOrder.isLeft()) {
                        daoOI.delete(orderItem1);
                        daoOI.delete(orderItem2);
                        return Either.left("Error while adding order");
                    } else {
                        return Either.right(null);
                    }
                }
            }
        }
    }
}
