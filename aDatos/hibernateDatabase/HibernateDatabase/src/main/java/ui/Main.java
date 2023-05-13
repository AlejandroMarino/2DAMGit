package ui;


import domain.model.*;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesCustomer;
import services.ServicesMenuItem;
import services.ServicesOrder;
import services.ServicesOrderItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesOrderItem sOI = container.select(ServicesOrderItem.class).get();
        ServicesCustomer sC = container.select(ServicesCustomer.class).get();
        ServicesMenuItem sMI = container.select(ServicesMenuItem.class).get();
        ServicesOrder sO = container.select(ServicesOrder.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Crud of customer" +
                    "\n\t2- Get information of the order-items of a order" +
                    "\n\t3- Get the name of the items ordered by a customer" +
                    "\n\t4- Add order with two order-items" +
                    "\n\t5- Delete a customer with all the orders" +
                    "\n\t6- Show all orders by customer" +
                    "\n\t7- Append a new menu item" +
                    "\n\t8- Delete a menu item" +
                    "\n\t9- Get the orders of a customer, showing the name of the customer and the number of seats" +
                    "\n\t10- Get the number of items of each order" +
                    "\n\t11- Get the name of the customer that has spent more money" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    crudCustomer(sC, sc);
                    break;
                case 2:
                    getOrderItemsOfOrder(sOI, sc);
                    break;
                case 3:
                    getNameOfItemsOrderedByCustomer(sMI, sc);
                    break;
                case 4:
                    addOrderWithTwoOrderItems(sO, sc);
                    break;
                case 5:
                    deleteCustomer(sC, sc, true);
                    break;
                case 6:
                    getAllCustomers(sC, true);
                    break;
                case 7:
                    addMenuItem(sMI, sc);
                    break;
                case 8:
                    //ver si hay que eliminar el order entero
                    System.out.println("Introduce the id of the menu item to delete");
                    int idMI = sc.nextInt();
                    sc.nextLine();
                    Either<Integer, Void> l = sMI.delete(new MenuItem(idMI), false);
                    if (l.isLeft()) {
                        if (l.getLeft() == -1) {
                            System.out.println("Error deleting menu item");
                        } else if (l.getLeft() == -2) {
                            System.out.println("There are orders with this menu item, ¿Do you want to delete them? (y/n)");
                            String answer = sc.nextLine();
                            if (answer.equals("y")) {
                                Either<Integer, Void> l2 = sMI.delete(new MenuItem(idMI), true);
                                if (l2.isLeft()) {
                                    System.out.println("Error deleting menu item");
                                } else {
                                    System.out.println("Menu item deleted");
                                }
                            } else {
                                System.out.println("Menu item not deleted");
                            }
                        }
                    } else {
                        System.out.println("Menu item deleted");
                    }
                    break;
                case 9:
                    getOrdersOfCustomerWithNameAndNumberOfSeats(sO, sc);
                    break;
                case 10:
                    getOrdersWithNumberOfItems(sO);
                    break;
                case 11:
                    System.out.println(sC.getCustomerSpentMost());
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (option != 0);
    }

    private static void getOrdersWithNumberOfItems(ServicesOrder sO) {
        Either<String, List<String>> r = sO.getOrdersWithNumberOfItems();
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            r.get().forEach(System.out::println);
        }
    }

    private static void getOrdersOfCustomerWithNameAndNumberOfSeats(ServicesOrder sO, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        int idC = sc.nextInt();
        sc.nextLine();
        Either<String, List<Order>> l = sO.getAllOfCustomer(new Customer(idC));
        if (l.isLeft()) {
            System.out.println(l.getLeft());
        } else {
            l.get().forEach(order ->
                    System.out.println(order.toStringWithCustomerNameAndOrderItems())
            );
        }
    }

    private static void getAllCustomers(ServicesCustomer sC, boolean withOrders) {
        Either<String, List<Customer>> l = sC.getAll(withOrders);
        if (l.isLeft()) {
            System.out.println(l.getLeft());
        } else if (withOrders) {
            l.get().forEach(customer -> System.out.println(customer.toStringWithOrders()));
        } else {
            l.get().forEach(customer -> System.out.println(customer.toString()));
        }
    }

    private static void addMenuItem(ServicesMenuItem sMI, Scanner sc) {
        System.out.println("Introduce the name of the menu item");
        String nameMI = sc.nextLine();
        System.out.println("Introduce the description of the menu item");
        String descriptionMI = sc.nextLine();
        System.out.println("Introduce the price of the menu item");
        double priceMI = sc.nextDouble();
        sc.nextLine();
        Either<String, Void> l = sMI.save(new MenuItem(nameMI, descriptionMI, priceMI));
        if (l.isLeft()) {
            System.out.println(l.getLeft());
        } else {
            System.out.println("Menu item saved");
        }
    }

    private static void addOrderWithTwoOrderItems(ServicesOrder sO, Scanner sc) {
        System.out.println("Introduce the id of the table");
        int idT = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the id of the customer");
        int idC = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the menu item");
        String nameMI1 = sc.nextLine();
        System.out.println("Introduce the quantity");
        int quantity1 = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the menu item");
        String nameMI2 = sc.nextLine();
        System.out.println("Introduce the quantity");
        int quantity2 = sc.nextInt();
        sc.nextLine();
        OrderItem oI1 = new OrderItem(new MenuItem(nameMI1), quantity1);
        OrderItem oI2 = new OrderItem(new MenuItem(nameMI2), quantity2);
        Either<String, Void> r = sO.save(new Order(new Table(idT), new Customer(idC), LocalDate.now(), false, List.of(oI1, oI2)));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Order added");
        }
    }

    private static void getNameOfItemsOrderedByCustomer(ServicesMenuItem sMI, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, List<String>> r = sMI.getMenuItemsOrderedByCustomer(id);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println(r.get());
        }
    }

    private static void getOrderItemsOfOrder(ServicesOrderItem sOI, Scanner sc) {
        System.out.println("Introduce the id of the order");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, List<OrderItem>> r = sOI.getAll(new Order(id));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            r.get().forEach(orderItem -> System.out.println(orderItem.toString()));
        }
    }

    private static void crudCustomer(ServicesCustomer sC, Scanner sc) {
        int opt;
        System.out.println("\nWhat you want to do?" +
                "\n\t1- Show all customers" +
                "\n\t2- Show a customer" +
                "\n\t3- Add a new customer" +
                "\n\t4- Update a customer" +
                "\n\t5- Delete a customer");
        opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                getAllCustomers(sC, false);
                break;
            case 2:
                showACustomer(sC, sc);
                break;
            case 3:
                addCustomer(sC, sc);
                break;
            case 4:
                updateCustomer(sC, sc);
                break;
            case 5:
                deleteCustomer(sC, sc, false);
                break;
            default:
                System.out.println("Introduce a valid option");
                break;
        }
    }

    private static void deleteCustomer(ServicesCustomer sC, Scanner sc, boolean withOrders) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, Void> r = sC.delete(id, withOrders);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Customer deleted");
        }
    }

    private static void updateCustomer(ServicesCustomer sC, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the first name of the customer");
        String fName = sc.nextLine();
        System.out.println("Introduce the last name of the customer");
        String lName = sc.nextLine();
        System.out.println("Introduce the email of the customer");
        String email = sc.nextLine();
        System.out.println("Introduce the phone of the customer");
        String phone = sc.nextLine();
        Either<String, Void> r = sC.update(new Customer(id, fName, lName, email, phone));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Customer updated");
        }
    }

    private static void addCustomer(ServicesCustomer sC, Scanner sc) {
        System.out.println("Introduce the first name of the customer");
        String fName = sc.nextLine();
        System.out.println("Introduce the last name of the customer");
        String lName = sc.nextLine();
        System.out.println("Introduce the email of the customer");
        String email = sc.nextLine();
        System.out.println("Introduce the phone of the customer");
        String phone = sc.nextLine();
        Either<String, Void> r = sC.add(new Customer(fName, lName, email, phone));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Customer added");
        }
    }

    private static void showACustomer(ServicesCustomer sC, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, Customer> r = sC.get(id);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println(r.get());
        }
    }
}
