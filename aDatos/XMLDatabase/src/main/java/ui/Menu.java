package ui;

import domain.model.txt.Customer;
import domain.model.txt.MenuItem;
import domain.model.txt.Order;
import domain.model.xml.Item;
import io.vavr.control.Either;
import services.servicestxt.ServicesCustomers;
import services.servicestxt.ServicesMenuItems;
import services.servicestxt.ServicesOrders;
import services.servicesxml.ServicesXml;
import services.servicesxml.servicesImpl.ServicesXmlImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public void menu(Scanner sc, ServicesCustomers sC, ServicesMenuItems sMI, ServicesOrders sO, ServicesXml sX) {
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Show all customers" +
                    "\n\t2- Show orders by customers" +
                    "\n\t3- append a new order with two items" +
                    "\n\t4- delete a customer" +
                    "\n\t5- generate xml" +
                    "\n\t6- get customers that have ordered an item" +
                    "\n\t7- append a new order xml" +
                    "\n\t8- delete a customer xml" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    getAllCustomers(sC);
                    break;
                case 2:
                    getOrdersByCustomers(sc, sX);
                    break;
                case 3:
                    addOrder(sc, sC, sMI, sO);
                    break;
                case 4:
                    deleteCustomer(sc, sC, sO);
                    break;
                case 5:
                    generateXml(sX);
                    break;
                case 6:
                    getCustomersByItem(sc, sX);
                    break;
                case 7:
                    addOrderXml(sc, sX);
                    break;
                case 8:
                    deleteCustomerXml(sc, sX);
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

    private static void deleteCustomerXml(Scanner sc, ServicesXml sX) {
        System.out.println("Which is the first name of the customer you want to delete?");
        String firstName = sc.nextLine().toLowerCase().trim();
        Either<String, Void> result = sX.deleteCustomer(firstName);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Customer deleted");
        }
    }

    private static void addOrderXml(Scanner sc, ServicesXml sX) {
        System.out.println("What is the first name of the customer?");
        String firstName = sc.nextLine().toLowerCase().trim();
        System.out.println("How many different items do you want to order?");
        int n = sc.nextInt();
        sc.nextLine();
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("What is the name of the item?");
            String name = sc.nextLine().toLowerCase().trim();
            System.out.println("What is the price of the item?");
            double price = sc.nextDouble();
            sc.nextLine();
            System.out.println("What is the quantity?");
            int quantity = sc.nextInt();
            sc.nextLine();
            items.add(new Item(name, price * quantity, quantity));
        }
        Either<String, Void> response = sX.addOrder(items, firstName);
        if (response.isLeft()) {
            System.out.println(response.getLeft());
        } else {
            System.out.println("Order added");
        }
    }

    private static void getCustomersByItem(Scanner sc, ServicesXml sX) {
        System.out.println("What is the name of the item?");
        String name = sc.nextLine().toLowerCase().trim();
        Either<String, List<domain.model.xml.Customer>> customers = sX.getCustomersByItem(name);
        if (customers.isLeft()) {
            System.out.println(customers.getLeft());
        } else {
            System.out.println("The customers that have ordered the item are:");
            customers.get().forEach(System.out::println);
        }
    }

    private static void generateXml(ServicesXml sX) {
        Either<String, Void> response = sX.generateXml();
        if (response.isLeft()) {
            System.out.println(response.getLeft());
        } else {
            System.out.println("XML generated");
        }
    }

    private static void addOrder(Scanner sc, ServicesCustomers sC, ServicesMenuItems sMI, ServicesOrders sO) {
        System.out.println("What is the first name of the customer?");
        String name = sc.nextLine().toLowerCase().trim();
        Either<String, Customer> customer = sC.getCustomer(name);
        if (customer.isLeft()) {
            System.out.println(customer.getLeft());
        } else {
            System.out.println("Which is the table number?");
            int table = sc.nextInt();
            sc.nextLine();
            Either<String, List<MenuItem>> items = sMI.getAllMenuItems();
            if (items.isLeft()) {
                System.out.println(items.getLeft());
            } else {
                System.out.println("Menu items: ");
                items.get().forEach(System.out::println);
                System.out.println("What is the name of the first item?");
                String name1 = sc.nextLine().toLowerCase().trim();
                System.out.println("How many of it do you want?");
                int quantity1 = sc.nextInt();
                sc.nextLine();
                System.out.println("What is the name of the second item?");
                String name2 = sc.nextLine().toLowerCase().trim();
                System.out.println("How many of it do you want?");
                int quantity2 = sc.nextInt();
                sc.nextLine();
                Either<String, Void> orderAdded = sO.addOrder(customer.get(), table, name1, quantity1, name2, quantity2);
                if (orderAdded.isLeft()) {
                    System.out.println(orderAdded.getLeft());
                } else {
                    System.out.println("Order added");
                }
            }
        }
    }

    private static void deleteCustomer(Scanner sc, ServicesCustomers sC, ServicesOrders sO) {
        System.out.println("What is the first name of the customer you want to delete?");
        String name = sc.nextLine().toLowerCase().trim();
        Either<String, List<Order>> customerOrders = sO.getOrdersByCustomer(name);
        if (customerOrders.isRight()) {
            if (customerOrders.get().isEmpty()) {
                boolean deleted = sC.deleteCustomer(name);
                if (deleted) {
                    System.out.println("Customer deleted");
                } else {
                    System.out.println("Error while deleting customer");
                }
            } else {
                String answer;
                do {
                    System.out.println("This customer has the orders: ");
                    customerOrders.get().forEach(System.out::println);
                    System.out.println("Do you want to delete it? (y/n)");
                    answer = sc.nextLine().toLowerCase().trim();
                    switch (answer) {
                        case "y" -> {
                            sO.deleteOrders(customerOrders.get());
                            boolean deleted = sC.deleteCustomer(name);
                            if (deleted) {
                                System.out.println("Customer deleted");
                            } else {
                                System.out.println("Error while deleting customer");
                            }
                        }
                        case "n" -> System.out.println("Customer not deleted");
                        default -> System.out.println("Introduce a valid option");
                    }
                } while (!answer.equals("y") && !answer.equals("n"));
            }
        } else {
            System.out.println(customerOrders.getLeft());
        }
    }

    private static void getOrdersByCustomers(Scanner sc, ServicesXml sX) {
        System.out.println("Introduce his first name of the customer");
        String name = sc.nextLine().toLowerCase().trim();
        Either<String, List<domain.model.xml.Order>> result = sX.getOrdersOfCustomer(name);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Orders: ");
            result.get().forEach(System.out::println);
        }
    }

    private static void getAllCustomers(ServicesCustomers sC) {
        Either<String, List<Customer>> result = sC.getAllCustomers();
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Customers: ");
            result.get().forEach(System.out::println);
        }
    }
}
