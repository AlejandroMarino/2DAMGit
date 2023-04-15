package ui;


import domain.model.Customer;
import domain.model.MenuItem;
import domain.model.Order;
import io.vavr.control.Either;
import services.ServicesCustomer;
import services.ServicesOrder;
import services.ServicesMenuItem;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuAdmin {

    public void menu(Scanner sc, ServicesCustomer sC, ServicesOrder sO, ServicesMenuItem sMI) {
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Show all customers" +
                    "\n\t2- Show the orders of a customer" +
                    "\n\t3- Append a new order with two items" +
                    "\n\t4- Delete a customer" +
                    "\n\t5- Get the 2 customers that have spent the less money in the restaurant" +
                    "\n\t6- Get the most ordered item" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    showAllCustomers(sC);
                    break;
                case 2:
                    getAllOrdersOfACustomer(sc, sO);
                    break;
                case 3:
                    appendNewOrder(sc, sO);
                    break;
                case 4:
                    deleteCustomer(sc, sC, sO);
                    break;
                case 5:
                    Either<String, List<Customer>> customers = sC.get2CSpentLessMoney();
                    if (customers.isLeft()) {
                        System.out.println(customers.getLeft());
                    } else {
                        System.out.println(customers.get());
                    }
                    break;
                case 6:
                    Either<String, Map<MenuItem, Integer>> orderedItem = sMI.getMostOrderedItem();
                    if (orderedItem.isLeft()) {
                        System.out.println(orderedItem.getLeft());
                    } else {
                        System.out.println(orderedItem.get());
                    }
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

    private static void deleteCustomer(Scanner sc, ServicesCustomer sC, ServicesOrder sO) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, Map<Order, Double>> customerOrders = sO.getAllOrdersOfCustomer(id);
        if (customerOrders.isLeft()) {
            if (customerOrders.getLeft().equals("Error while getting all orders")) {
                System.out.println(customerOrders.getLeft());
            } else {
                Either<String, Void> deleted = sC.deleteCustomer(id);
                if (deleted.isLeft()) {
                    System.out.println(deleted.getLeft());
                } else {
                    System.out.println("The customer has been deleted");
                }
            }
        } else {
            if (customerOrders.get().isEmpty()) {
                Either<String, Void> deleted = sC.deleteCustomer(id);
                if (deleted.isLeft()) {
                    System.out.println(deleted.getLeft());
                } else {
                    System.out.println("The customer has been deleted");
                }
            } else {
                System.out.println("The customer has orders, do you want to delete them? (y/n)");
                String answer = sc.nextLine().toLowerCase().trim();
                switch (answer) {
                    case "y":
                        Either<String, Void> deleted = sC.deleteCustomer(id);
                        if (deleted.isLeft()) {
                            System.out.println(deleted.getLeft());
                        } else {
                            System.out.println("The customer has been deleted");
                        }
                        break;
                    case "n":
                        System.out.println("The customer has not been deleted");
                        break;
                    default:
                        System.out.println("Introduce a valid option");
                        break;
                }
            }
        }
    }

    private static void appendNewOrder(Scanner sc, ServicesOrder sO) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the table id");
        int tableId = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the first item");
        String name1 = sc.nextLine();
        System.out.println("Introduce the quantity that you want");
        int quantity1 = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the second item");
        String name2 = sc.nextLine();
        System.out.println("Introduce the quantity that you want");
        int quantity2 = sc.nextInt();
        sc.nextLine();
        Either<String, Void> result = sO.addOrder(id, tableId, name1, quantity1, name2, quantity2);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Order added");
        }
    }

    private static void getAllOrdersOfACustomer(Scanner sc, ServicesOrder sO) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        Either<String, Map<Order, Double>> result = sO.getAllOrdersOfCustomer(id);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println(result.get());
        }
    }

    private static void showAllCustomers(ServicesCustomer sC) {
        Either<String, List<Customer>> result = sC.getAllCustomers();
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Customers: ");
            result.get().forEach(System.out::println);
        }
    }


}
