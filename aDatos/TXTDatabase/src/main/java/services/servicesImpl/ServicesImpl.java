package services.servicesImpl;

import dao.DaoCustomers;
import dao.DaoMenuItems;
import dao.DaoOrderItems;
import dao.DaoOrders;
import domain.model.Customer;
import domain.model.MenuItem;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class ServicesImpl implements Services {

    private final DaoCustomers daoC;
    private final DaoOrders daoO;
    private final DaoOrderItems daoOI;
    private final DaoMenuItems daoMI;

    @Inject
    public ServicesImpl(DaoCustomers daoC, DaoOrders daoO, DaoOrderItems daoOI, DaoMenuItems daoMI) {
        this.daoC = daoC;
        this.daoO = daoO;
        this.daoOI = daoOI;
        this.daoMI = daoMI;
    }

    @Override
    public Either<String, List<Customer>> getAllCustomers() {
        Either<Integer, List<Customer>> result = daoC.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting all customers");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Either<String, List<Order>> getOrdersByCustomer(String name) {
        Either<Integer, List<Order>> result = daoO.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting orders");
        } else {
            Either<String, Customer> customer = getCustomer(name);
            if (customer.isLeft()) {
                return Either.left(customer.getLeft());
            } else {
                Customer c = customer.get();
                List<Order> orders = result.get().stream().filter(order -> order.getCustomerId() == c.getId()).toList();
                return Either.right(orders);
            }
        }
    }

    @Override
    public Boolean deleteCustomer(String name) {
        Either<String, Customer> customer = getCustomer(name);
        if (customer.isLeft()) {
            return false;
        } else {
            Customer c = customer.get();
            Either<Integer, List<Order>> orders = daoO.getAll();
            if (orders.isLeft()) {
                return false;
            } else {
                Either<Integer, Void> result = daoC.delete(c);
                return !result.isLeft();
            }
        }
    }

    @Override
    public void deleteOrders(List<Order> orders) {
        orders.forEach(order -> {
            Either<String, List<OrderItem>> orderItems = getOrderItemsOfOrder(order);
            if (orderItems.isLeft()) {
                return;
            } else {
                orderItems.get().forEach(daoOI::delete);
            }
            daoO.delete(order);
        });
    }

    @Override
    public Either<String, List<MenuItem>> getAllMenuItems() {
        Either<Integer, List<MenuItem>> result = daoMI.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting menu items");
        } else {
            return Either.right(result.get());
        }
    }

    @Override
    public Either<String, Void> addOrder(Customer customer, int table, String name1, int quantity1, String name2, int quantity2) {
        Random r = new Random();
        MenuItem item1 = getMenuItem(name1);
        MenuItem item2 = getMenuItem(name2);
        if (item1 == null || item2 == null) {
            return Either.left("One or more menu items not found");
        } else {
            double priceItems1 = item1.getPrice()*quantity1;
            double priceItems2 = item2.getPrice()*quantity2;
            double totalPrice = priceItems1 + priceItems2;
            Order order = new Order(r.nextInt(),table,customer.getId(), LocalDate.now(), totalPrice);
            OrderItem orderItem1 = new OrderItem(r.nextInt(), order.getId(), item1.getId(), quantity1, priceItems1);
            OrderItem orderItem2 = new OrderItem(r.nextInt(), order.getId(), item2.getId(), quantity2, priceItems2);
            Either<Integer,Void> rAddItem1 = daoOI.save(orderItem1);
            if (rAddItem1.isLeft()) {
                return Either.left("Error while adding the first order item");
            } else {
                Either<Integer,Void> rAddItem2 = daoOI.save(orderItem2);
                if (rAddItem2.isLeft()) {
                    daoOI.delete(orderItem1);
                    return Either.left("Error while adding the second order item");
                } else {
                    Either<Integer,Void> rAddOrder = daoO.save(order);
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

    @Override
    public Either<String, Customer> getCustomer(String name) {
        Either<Integer, List<Customer>> customers = daoC.getAll();
        if (customers.isLeft()) {
            return Either.left("Error while getting customers");
        } else {
            Customer c = customers.get().stream().filter(customer -> customer.getFirstName().toLowerCase().equals(name))
                    .findFirst().orElse(null);
            if (c == null) {
                return Either.left("Customer not found");
            } else {
                return Either.right(c);
            }
        }
    }

    private MenuItem getMenuItem(String name) {
        Either<Integer, List<MenuItem>> result = daoMI.getAll();
        if (result.isLeft()) {
            return null;
        } else {
            return result.get().stream().filter(menuItem -> menuItem.getName().toLowerCase().equals(name)).findFirst().orElse(null);
        }
    }

    private Either<String, List<OrderItem>> getOrderItemsOfOrder(Order order) {
        Either<Integer, List<OrderItem>> result = daoOI.getAll();
        if (result.isLeft()) {
            return Either.left("Error while getting order items");
        } else {
            List<OrderItem> orderItems = result.get().stream().filter(orderItem -> orderItem.getOrderId() == order.getId()).toList();
            return Either.right(orderItems);
        }
    }


}
