package services.servicesImpl;

import dao.daoHibernate.DaoCustomer;
import dao.daoHibernate.DaoMenuItem;
import dao.daoMongo.DaoMongo;
import domain.model.modelMongo.*;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesMongo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.emptyList;

public class ServicesMongoImpl implements ServicesMongo {

    private final DaoMongo daoMongo;
    private final DaoMenuItem dMI;
    private final DaoCustomer dC;

    @Inject
    public ServicesMongoImpl(DaoMongo daoMongo, DaoMenuItem dMI, DaoCustomer dC) {
        this.daoMongo = daoMongo;
        this.dMI = dMI;
        this.dC = dC;
    }

    @Override
    public Either<String, Void> saveMongo() {
        Either<Integer, List<domain.model.modelMongo.Customer>> customers = convertCustomers();
        Either<Integer, List<MenuItem>> menuItems = convertMenuItems();
        if (customers.isLeft() || menuItems.isLeft()) {
            return Either.left("Error converting customers or menuItems");
        } else {
            daoMongo.saveMongo(customers.get(), menuItems.get());
            return Either.right(null);
        }
    }

    private Either<Integer, List<Customer>> convertCustomers() {
        Either<Integer, List<domain.model.modelHibernate.Customer>> customers = dC.getAll(true);
        if (customers.isLeft()) {
            return Either.left(customers.getLeft());
        } else {
            List<domain.model.modelMongo.Customer> customersMongo = new ArrayList<>(emptyList());
            for (domain.model.modelHibernate.Customer customer : customers.get()) {
                List<Order> orders = new ArrayList<>(emptyList());
                customer.getOrders().forEach(order -> {
                    AtomicReference<Double> total = new AtomicReference<>((double) 0);
                    List<OrderItem> orderItems = new ArrayList<>(emptyList());
                    order.getOrderItems().forEach(orderItem -> {
                        domain.model.modelHibernate.MenuItem mi = orderItem.getMenuItem();
                        total.set(mi.getPrice() * orderItem.getQuantity());
                        OrderItem oi = new OrderItem(mi.getId(), mi.getName(), orderItem.getQuantity());
                        orderItems.add(oi);
                    });
                    Table table = new Table(order.getTable().getNumber(), order.getTable().getNumberOfSeats());
                    Order orderMongo = new domain.model.modelMongo.Order(order.getDate().toString(), total.get(), order.isPaid(), table, orderItems);
                    orders.add(orderMongo);
                });
                String name = customer.getFirstname() + " " + customer.getLastname();
                domain.model.modelMongo.Customer customerMongo = new domain.model.modelMongo.Customer(name, orders);
                customersMongo.add(customerMongo);
            }
            return Either.right(customersMongo);
        }
    }

    private Either<Integer, List<MenuItem>> convertMenuItems() {
        Either<Integer, List<domain.model.modelHibernate.MenuItem>> menuItems = dMI.getAll();
        if (menuItems.isLeft()) {
            return Either.left(menuItems.getLeft());
        } else {
            List<MenuItem> menuItemsMongo = new ArrayList<>(emptyList());
            menuItems.get().forEach(menuItem -> {
                MenuItem menuItemMongo = new MenuItem(menuItem.getId(), menuItem.getName(), menuItem.getDescription(), menuItem.getPrice());
                menuItemsMongo.add(menuItemMongo);
            });
            return Either.right(menuItemsMongo);
        }
    }
}
