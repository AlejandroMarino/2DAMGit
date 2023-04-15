package ui;

import io.vavr.control.Either;
import services.ServicesOrder;

import java.util.Scanner;

public class MenuCustomer {

    public void menu(Scanner sc, int customerId, ServicesOrder sO) {
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Append a new order with two items" +
                    "\n\t2- Edit an order" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    appendNewOrder(sc, customerId, sO);
                    break;
                case 2:
                    editOrder(sc, customerId, sO);
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

    private static void editOrder(Scanner sc, int customerId, ServicesOrder sO) {
        System.out.println("Introduce the order id");
        int orderId = sc.nextInt();
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
        Either<String, Void> result = sO.editOrder(customerId, orderId, tableId, name1, quantity1, name2, quantity2);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Order edited");
        }
    }

    private static void appendNewOrder(Scanner sc, int customerId, ServicesOrder sO) {
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
        Either<String, Void> result = sO.addOrder(customerId, tableId, name1, quantity1, name2, quantity2);
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println("Order added");
        }
    }
}
