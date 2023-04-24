package ui;


import domain.model.spring.*;
import io.vavr.control.Either;
import services.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuAdmin {

    public void menu(Scanner sc, ServicesOrders sO, ServicesOrderItems sOI, ServicesMenuItems sMI, ServicesQueries sQ, ServicesXML sX) {
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Show the order items of each order" +
                    "\n\t2- Add a new order item to the last order of a customer" +
                    "\n\t3- Modify the price of a menu item" +
                    "\n\t4- BackUp all paid orders older than the current year into an XML file" +
                    "\n\t5- Show the most requested table in 2023" +
                    "\n\t6- Show the name and the quantity of each of the items requested, grouped by table" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    showOrders(sO);
                    break;
                case 2:
                    addOrderItem(sc, sOI);
                    break;
                case 3:
                    modifyPrice(sc, sMI);
                    break;
                case 4:
                    saveXml(sX);
                    break;
                case 5:
                    getMostRequestedTable(sQ);
                    break;
                case 6:
                    showItemsRequestedPerTable(sQ);
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

    private static void saveXml(ServicesXML sX) {
        Either<String, Void> r = sX.savePaidOrders();
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Orders saved");
        }
    }

    private static void showItemsRequestedPerTable(ServicesQueries sQ) {
        Either<String, Map<Table, List<ItemQuery>>> result = sQ.getItemsRequestedTables();
        if (result.isLeft()) {
            System.out.println(result.getLeft());
        } else {
            System.out.println(result.get());
        }
    }

    private static void getMostRequestedTable(ServicesQueries sQ) {
        Either<String, Table> table = sQ.getMostRequestedTable();
        if (table.isLeft()) {
            System.out.println(table.getLeft());
        } else {
            System.out.println(table.get());
        }
    }

    private static void modifyPrice(Scanner sc, ServicesMenuItems sMI) {
        System.out.println("Introduce the name of the item you want to modify");
        String name = sc.nextLine();
        System.out.println("Introduce the new price");
        double price = sc.nextDouble();
        sc.nextLine();
        Either<String, Void> modifyPrice = sMI.modifyPrice(new MenuItem(name, price));
        if (modifyPrice.isLeft()) {
            System.out.println(modifyPrice.getLeft());
        } else {
            System.out.println("Price modified");
        }
    }

    private static void addOrderItem(Scanner sc, ServicesOrderItems sOI) {
        System.out.println("Introduce the id of the customer");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the item you want to add to the order");
        String name = sc.nextLine();
        System.out.println("Introduce the quantity that you want");
        int quantity = sc.nextInt();
        sc.nextLine();
        Customer c = new Customer(id);
        MenuItem mI = new MenuItem(name);
        OrderItem oI = new OrderItem(quantity);
        Either<String, Void> addOrderItem = sOI.addOrderItem(mI, oI, c);
        if (addOrderItem.isLeft()) {
            System.out.println(addOrderItem.getLeft());
        } else {
            System.out.println("Order item added");
        }
    }

    private static void showOrders(ServicesOrders sO) {
        Either<String, Map<Order, List<OrderItem>>> orders = sO.getAllOrdersWItems();
        if (orders.isLeft()) {
            System.out.println(orders.getLeft());
        } else {
            System.out.println(orders.get());
        }
    }

}
