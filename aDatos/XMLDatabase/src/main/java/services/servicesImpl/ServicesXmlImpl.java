package services.servicesImpl;

import dao.DaoMenuItems;
import dao.DaoXml;
import domain.model.txt.Customer;
import domain.model.xml.Customers;
import domain.model.xml.Item;
import domain.model.xml.Items;
import domain.model.xml.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.*;

import java.util.List;

import static java.util.Collections.emptyList;

public class ServicesXmlImpl implements ServicesXml {

    private final DaoXml dXml;
    private final DaoMenuItems dMI;
    private final ServicesCustomers sC;
    private final ServicesOrderItems sOI;
    private final ServicesOrders sO;
    private final ServicesMenuItems sMI;

    @Inject
    public ServicesXmlImpl(DaoXml dXml, DaoMenuItems dMI, ServicesCustomers sC, ServicesOrderItems sOI, ServicesOrders sO, ServicesMenuItems sMI) {
        this.dXml = dXml;
        this.dMI = dMI;
        this.sC = sC;
        this.sOI = sOI;
        this.sO = sO;
        this.sMI = sMI;
    }

    public Either<String, Void> add() {
        List<Customer> customers = sC.getAllCustomers().get();
        List<domain.model.xml.Customer> customersXml = new java.util.ArrayList<>(emptyList());
        customers.forEach(customer -> {
            List<Order> orders = new java.util.ArrayList<>(emptyList());
            sO.getOrdersByCustomer(customer.getFirstName().toLowerCase()).get().forEach(order -> {
                List<Item> items = new java.util.ArrayList<>(emptyList());
                sOI.getOrderItemsOfOrder(order).get().forEach(orderItem -> {
                    String name = dMI.get(orderItem.getMenuItemId()).get().getName();
                    Item item = new Item(name, orderItem.getPrice(), orderItem.getQuantity());
                    items.add(item);
                });
                Order orderXml = new Order(order.getId(), order.getOrderDate(), order.getTotal(), new Items(items));
                orders.add(orderXml);
            });
            domain.model.xml.Customer customerXml = new domain.model.xml.Customer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), orders);
            customersXml.add(customerXml);
        });
        Customers customers1 = new Customers(customersXml);
        Either<Integer, Void> response = dXml.add(customers1);
        if (response.isLeft()) {
            if (response.getLeft() == -2) {
                return Either.left("Error marshalling");
            } else {
                return Either.left("Error while adding customers");
            }
        } else {
            return Either.right(null);
        }
    }
}
