package services.servicesxml.servicesImpl;

import dao.daotxt.DaoMenuItems;
import dao.daoxml.DaoXml;
import domain.model.txt.Customer;
import domain.model.xml.Customers;
import domain.model.xml.Item;
import domain.model.xml.Items;
import domain.model.xml.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.servicestxt.ServicesCustomers;
import services.servicestxt.ServicesOrderItems;
import services.servicestxt.ServicesOrders;
import services.servicesxml.ServicesXml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.util.Collections.emptyList;

public class ServicesXmlImpl implements ServicesXml {

    private final DaoXml dXml;
    private final DaoMenuItems dMI;
    private final ServicesCustomers sC;
    private final ServicesOrderItems sOI;
    private final ServicesOrders sO;

    @Inject
    public ServicesXmlImpl(DaoXml dXml, DaoMenuItems dMI, ServicesCustomers sC, ServicesOrderItems sOI, ServicesOrders sO) {
        this.dXml = dXml;
        this.dMI = dMI;
        this.sC = sC;
        this.sOI = sOI;
        this.sO = sO;
    }

    public Either<String, Void> generateXml() {
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
        Either<Integer, Void> response = dXml.saveXml(customers1);
        if (response.isLeft()) {
            return Either.left("Error while adding customers");
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<String, List<Order>> getOrdersOfCustomer(String name) {
        Either<String, Customers> response = getCustomers();
        if (response.isLeft()) {
            return Either.left(response.getLeft());
        } else {
            Customers customers = response.get();
            List<domain.model.xml.Customer> customersXml = customers.getCustomers();
            domain.model.xml.Customer c = customersXml.stream()
                    .filter(customer -> Objects
                            .equals(customer.getFirstName().trim().toLowerCase(), name.trim().toLowerCase()))
                    .findFirst().orElse(null);
            if (c == null) {
                return Either.left("Customer not found");
            } else {
                List<Order> orders = c.getOrders();
                if (orders == null || orders.isEmpty()) {
                    return Either.left("Customer has no orders");
                } else {
                    return Either.right(orders);
                }
            }
        }
    }

    @Override
    public Either<String, List<domain.model.xml.Customer>> getCustomersByItem(String name) {
        Either<String, Customers> response = getCustomers();
        if (response.isLeft()) {
            return Either.left(response.getLeft());
        } else {
            Customers customers = response.get();
            List<domain.model.xml.Customer> customersXml = customers.getCustomers();
            List<domain.model.xml.Customer> customersWithItem = new java.util.ArrayList<>(emptyList());
            customersXml.forEach(customer -> {
                List<Order> orders = customer.getOrders();
                if (orders != null && !orders.isEmpty()) {
                    orders.forEach(order -> {
                        List<Item> items = order.getItems().getItems();
                        items.forEach(item -> {
                            if (Objects.equals(item.getName().trim().toLowerCase(), name.trim().toLowerCase())) {
                                customersWithItem.add(customer);
                            }
                        });
                    });
                }
            });
            if (customersWithItem.isEmpty()) {
                return Either.left("No customers found");
            } else {
                return Either.right(customersWithItem);
            }
        }
    }

    @Override
    public Either<String, Void> addOrder(List<Item> items, String name) {
        Random r = new Random();
        Either<String, Customers> response = getCustomers();
        if (response.isLeft()) {
            return Either.left(response.getLeft());
        } else {
            Customers customers = response.get();
            List<domain.model.xml.Customer> customersXml = customers.getCustomers();
            domain.model.xml.Customer c = customersXml.stream()
                    .filter(customer -> Objects
                            .equals(customer.getFirstName().trim().toLowerCase(), name.trim().toLowerCase()))
                    .findFirst().orElse(null);
            if (c == null) {
                return Either.left("Customer not found");
            } else {
                List<Order> orders = c.getOrders();
                if (orders == null) {
                    orders = new ArrayList<>();
                }
                double totalPrice = 0;
                for (Item item : items) {
                    totalPrice += item.getPrice();
                }
                Order order = new Order(r.nextInt(), LocalDate.now(), totalPrice, new Items(items));
                orders.add(order);
                c.setOrders(orders);
                Either<Integer, Void> resp = dXml.saveXml(customers);
                if (resp.isLeft()) {
                    return Either.left("Error while adding order");
                } else {
                    return Either.right(null);
                }
            }
        }
    }

    @Override
    public Either<String, Void> deleteCustomer(String firstName) {
        Either<String, Customers> response = getCustomers();
        if (response.isLeft()) {
            return Either.left(response.getLeft());
        } else {
            Customers customers = response.get();
            List<domain.model.xml.Customer> customersXml = customers.getCustomers();
            domain.model.xml.Customer c = customersXml.stream()
                    .filter(customer -> Objects
                            .equals(customer.getFirstName().trim().toLowerCase(), firstName.trim().toLowerCase()))
                    .findFirst().orElse(null);
            if (c == null) {
                return Either.left("Customer not found");
            } else {
                customersXml.remove(c);
                Either<Integer, Void> resp = dXml.saveXml(customers);
                if (resp.isLeft()) {
                    return Either.left("Error while deleting customer");
                } else {
                    return Either.right(null);
                }
            }
        }
    }

    private Either<String, Customers> getCustomers() {
        Either<Integer, Customers> response = dXml.getAll();
        if (response.isLeft()) {
            return Either.left("Error while getting customers");
        } else {
            return Either.right(response.get());
        }
    }

}
